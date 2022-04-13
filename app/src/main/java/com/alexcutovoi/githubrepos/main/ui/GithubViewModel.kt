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
import com.alexcutovoi.githubrepos.main.domain.usecases.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel

class GithubViewModel(
    private val getRepositoriesUseCase: GetRepositoriesUseCase,
    private val getCachedRepositoriesUseCase: GetRepositoriesUseCase,
    private val saveRepositoriesUseCase: SaveRepositoriesUseCase,
    private val savePageUseCase: SavePageUseCase,
    private val getPageUseCase: GetPageUseCase) : ViewModel() {

    private val channel = Channel<Int>(capacity = 1)

    private val viewModelJob = SupervisorJob()

    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val repositoriesLiveData: MutableLiveData<ViewState<Repositories?>> by lazy {
        MutableLiveData<ViewState<Repositories?>>().also {
            viewModelScope.launch {
                isCacheEmpty()
            }
        }
    }

    fun getRepositories(){
        viewModelScope.launch {
            val repositoriesData = withContext(Dispatchers.Default) {
                repositoriesLiveData.postValue(ViewState.IsLoading<Repositories?>(null))

                getRepositoriesUseCase.getRepositories(0)
            }

            val repositoriesViewData = when(repositoriesData){
                is DataState.Success -> {
                    val list = (repositoriesData.data as Repositories).repositoryList
                    CoroutineScope(Dispatchers.Main).launch {
                        savePageUseCase.saveCurrentPage(0)
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
        viewModelScope.launch {
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
        viewModelScope.launch {
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
                        getRepositories()
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
        private val saveRepositoriesUseCase: SaveRepositoriesUseCase,
        private val savePageUseCase: SavePageUseCase,
        private val getPageUseCase: GetPageUseCase) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return GithubViewModel(getRepositoriesUseCase, getCachedRepositoriesUseCase, saveRepositoriesUseCase, savePageUseCase, getPageUseCase) as T
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}