package com.alexcutovoi.githubrepos.main.domain.usecases

import com.alexcutovoi.githubrepos.main.data.repository.GithubRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SavePageUseCase(private val githubRepository: GithubRepository) {
    suspend fun saveCurrentPage(pageNumber: Int) {
        withContext(Dispatchers.Default){
            githubRepository.saveCurrentPage(pageNumber)
        }
    }
}