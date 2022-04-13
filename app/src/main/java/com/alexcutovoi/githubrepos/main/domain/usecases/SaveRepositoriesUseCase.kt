package com.alexcutovoi.githubrepos.main.domain.usecases

import com.alexcutovoi.githubrepos.main.data.repository.GithubRepository
import com.alexcutovoi.githubrepos.main.domain.model.Repositories
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SaveRepositoriesUseCase(private val githubRepository: GithubRepository) {
    suspend fun saveRepositories(repositories: Repositories) {
        return withContext(Dispatchers.Default){
            githubRepository.saveRepos(repositories)
        }
    }
}