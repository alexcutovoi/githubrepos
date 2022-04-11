package com.alexcutovoi.githubrepos.main.domain.usecases

import com.alexcutovoi.githubrepos.common.DataState
import com.alexcutovoi.githubrepos.common.utils.Constants
import com.alexcutovoi.githubrepos.main.data.repository.GithubRepository
import com.alexcutovoi.githubrepos.main.domain.model.Repositories
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetRepositoriesUseCase(private val githubRepository: GithubRepository) {
    suspend fun getRepositories(page: Int): DataState<Repositories?> {
        return withContext(Dispatchers.Default){
            githubRepository.listRepos(Constants.LANGUAGE_QUERY, Constants.DEFAULT_SORT_ORDER, page)
        }
    }
}