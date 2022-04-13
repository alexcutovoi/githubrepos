package com.alexcutovoi.githubrepos.main.domain.usecases

import com.alexcutovoi.githubrepos.common.DataState
import com.alexcutovoi.githubrepos.main.data.repository.GithubRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class GetPageUseCase(private val githubRepository: GithubRepository) {
    suspend fun getCurrentPage(): DataState<Int> {
        return withContext(Dispatchers.Default) {
            githubRepository.getPageNumber()
        }
    }
}