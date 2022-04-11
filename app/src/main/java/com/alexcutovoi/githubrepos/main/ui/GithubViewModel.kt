package com.alexcutovoi.githubrepos.main.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alexcutovoi.githubrepos.common.DataState
import com.alexcutovoi.githubrepos.main.domain.model.Repositories
import com.alexcutovoi.githubrepos.main.domain.usecases.GetRepositoriesUseCase
import kotlinx.coroutines.*

class GithubViewModel(private val getRepositoriesUseCase: GetRepositoriesUseCase) : ViewModel() {
    val repositoriesLiveData = MutableLiveData<ViewState<Repositories?>>()

    fun getRepositories(page: Int){
        CoroutineScope(Dispatchers.Main).launch {
            val repositoriesData = withContext(Dispatchers.Default) {
                repositoriesLiveData.postValue(ViewState.IsLoading<Repositories?>(null))
                getRepositoriesUseCase.getRepositories(page)
            }

            val repositoriesViewData = when(repositoriesData){
                is DataState.Success -> {
                    ViewState.Success<Repositories?>(repositoriesData.data)
                }
                is DataState.Error -> {
                    ViewState.Error<Repositories?>(repositoriesData.exception?.localizedMessage ?: "An error occurred")
                }
                else -> {
                    ViewState.Error<Repositories?>("An error occurred")
                }
            }

            repositoriesLiveData.postValue(repositoriesViewData)
        }
    }

    class GithubViewModelFactory(private val getRepositoriesUseCase: GetRepositoriesUseCase) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return GithubViewModel(getRepositoriesUseCase) as T
        }
    }
}