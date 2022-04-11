package com.alexcutovoi.githubrepos.main.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alexcutovoi.githubrepos.main.domain.model.Repositories
import com.alexcutovoi.githubrepos.main.domain.usecases.GetRepositoriesUseCase
import kotlinx.coroutines.*

class GithubViewModel(private val getRepositoriesUseCase: GetRepositoriesUseCase) : ViewModel() {
    val repositoriesLiveData = MutableLiveData<ViewState<Repositories?>>()

    fun getRepositories(page: Int){
        CoroutineScope(Dispatchers.Main).launch {
            val repositories = withContext(Dispatchers.Default) {
                repositoriesLiveData.postValue(ViewState.IsLoading<Repositories?>(null))
                getRepositoriesUseCase.getRepositories(page)
            }

            if(repositories == null){
                repositoriesLiveData.postValue(ViewState.Error<Repositories?>("",null))
            }
            repositoriesLiveData.postValue(ViewState.Success<Repositories?>(repositories))
        }
    }

    class GithubViewModelFactory(private val getRepositoriesUseCase: GetRepositoriesUseCase) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return GithubViewModel(getRepositoriesUseCase) as T
        }
    }
}