package com.alexcutovoi.githubrepos.main.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alexcutovoi.githubrepos.GithubApplication
import com.alexcutovoi.githubrepos.R
import com.alexcutovoi.githubrepos.common.DataState
import com.alexcutovoi.githubrepos.main.data.local.GithubDatabase
import com.alexcutovoi.githubrepos.main.data.repository.GithubRepositoryLocalImpl
import com.alexcutovoi.githubrepos.main.domain.model.Repositories
import com.alexcutovoi.githubrepos.main.domain.model.Repository
import com.alexcutovoi.githubrepos.main.domain.usecases.GetRepositoriesCountUseCase
import com.alexcutovoi.githubrepos.main.domain.usecases.GetRepositoriesUseCase
import com.alexcutovoi.githubrepos.main.domain.usecases.SaveRepositoriesUseCase
import kotlinx.coroutines.*

class GithubViewModel(
    private val getRepositoriesUseCase: GetRepositoriesUseCase,
    private val getCachedRepositoriesUseCase: GetRepositoriesUseCase,
    private val saveRepositoriesUseCase: SaveRepositoriesUseCase) : ViewModel() {

    val repositoriesLiveData: MutableLiveData<ViewState<Repositories?>> by lazy {
        MutableLiveData<ViewState<Repositories?>>().also {
            CoroutineScope(Dispatchers.Main).launch {
                withContext(Dispatchers.Default) {
                    isCacheEmpty()
                }
            }
        }
    }

    fun getRepositories(page: Int){
        CoroutineScope(Dispatchers.Main).launch {
            val repositoriesData = withContext(Dispatchers.Default) {
                repositoriesLiveData.postValue(ViewState.IsLoading<Repositories?>(null))
                getRepositoriesUseCase.getRepositories(page)
            }

            val repositoriesViewData = when(repositoriesData){
                is DataState.Success -> {
                    val list = (repositoriesData.data as Repositories).repositoryList
                    CoroutineScope(Dispatchers.Main).launch {
                        saveRepositoriesUseCase.saveRepositories(repositoriesData.data)
                    }

                    ViewState.Success<Repositories?>(Repositories(list, (repositoriesData.data as Repositories).totalCount))
                }
                is DataState.Error -> {
                    ViewState.Error<Repositories?>(repositoriesData.exception?.localizedMessage ?:
                        GithubApplication.getApplicationContext().getString(R.string.generic_error))
                }
                else -> {
                    ViewState.Error<Repositories?>(
                        GithubApplication.getApplicationContext().getString(R.string.generic_error))
                }
            }

            repositoriesLiveData.postValue(repositoriesViewData)
        }
    }

    private fun getLocalRepositories(){
        CoroutineScope(Dispatchers.Main).launch {
            val repositoriesData = withContext(Dispatchers.Default) {
                repositoriesLiveData.postValue(ViewState.IsLoading<Repositories?>(null))
                getCachedRepositoriesUseCase.getRepositories(0)
            }

            val repositoriesViewData = when(repositoriesData){
                is DataState.Success -> {
                    val list = (repositoriesData.data as Repositories).repositoryList
                    ViewState.Success<Repositories?>(Repositories(list, (repositoriesData.data as Repositories).totalCount))
                }
                is DataState.Error -> {
                    ViewState.Error<Repositories?>(repositoriesData.exception?.localizedMessage ?: GithubApplication.getApplicationContext().getString(
                        R.string.generic_error)
                    )
                }
                else -> {
                    ViewState.Error<Repositories?>(GithubApplication.getApplicationContext().getString(
                        R.string.generic_error)
                    )
                }
            }

            repositoriesLiveData.postValue(repositoriesViewData)
        }
    }

    private suspend fun isCacheEmpty() {
        CoroutineScope(Dispatchers.Main).launch {
            val repositoriesCountData = withContext(Dispatchers.Default) {
                val cacheUseCase = GetRepositoriesCountUseCase(
                    GithubRepositoryLocalImpl(
                        GithubDatabase.getDb().getGithubDao()
                    )
                )
                cacheUseCase.getRepositoriesCount()
            }

            when(repositoriesCountData) {
                is DataState.Success -> {
                    val hasData = (repositoriesCountData.data as Int) > 0
                    if(hasData) {
                        getLocalRepositories()
                    } else {
                        getRepositories(1)
                    }
                }
                is DataState.Error -> {
                    ViewState.Error<Repositories?>(repositoriesCountData.exception?.localizedMessage ?: GithubApplication.getApplicationContext().getString(
                        R.string.generic_error)
                    )
                }
                else -> {
                    ViewState.Error<Repositories?>(GithubApplication.getApplicationContext().getString(
                        R.string.generic_error)
                    )
                }
            }
        }
    }

    class GithubViewModelFactory(
        private val getRepositoriesUseCase: GetRepositoriesUseCase,
        private val getCachedRepositoriesUseCase: GetRepositoriesUseCase,
        private val saveRepositoriesUseCase: SaveRepositoriesUseCase) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return GithubViewModel(getRepositoriesUseCase, getCachedRepositoriesUseCase, saveRepositoriesUseCase) as T
        }
    }
}