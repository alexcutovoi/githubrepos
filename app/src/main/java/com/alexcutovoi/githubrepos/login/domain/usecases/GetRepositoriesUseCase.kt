package com.alexcutovoi.githubrepos.login.domain.usecases

import com.alexcutovoi.githubrepos.common.utils.Constants
import com.alexcutovoi.githubrepos.login.data.repository.GithubRepository
import com.alexcutovoi.githubrepos.login.domain.model.Repositories
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class GetRepositoriesUseCase(private val githubRepository: GithubRepository) {
    suspend fun getRepositories(page: Int): Repositories? {
        return withContext(Dispatchers.Default){
            githubRepository.listRepos(Constants.LANGUAGE_QUERY, Constants.DEFAULT_SORT_ORDER, page)
        }
    }
}