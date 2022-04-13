package com.alexcutovoi.githubrepos.main.domain.usecases

import com.alexcutovoi.githubrepos.common.DataState
import com.alexcutovoi.githubrepos.main.data.repository.GithubRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class GetRepositoriesCountUseCase(private val githubRepository: GithubRepository) {
    suspend fun getRepositoriesCount(): DataState<Int> {
        return withContext(Dispatchers.Default){
            githubRepository.getRepositoriesCount()
        }
    }
}