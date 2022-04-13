package com.alexcutovoi.githubrepos.main.data.repository

import com.alexcutovoi.githubrepos.common.DataState
import com.alexcutovoi.githubrepos.main.data.local.GithubDao
import com.alexcutovoi.githubrepos.main.data.local.entity.toRepository
import com.alexcutovoi.githubrepos.main.domain.model.Page
import com.alexcutovoi.githubrepos.main.domain.model.Repositories
import com.alexcutovoi.githubrepos.main.domain.model.Repository
import com.alexcutovoi.githubrepos.main.domain.model.toEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GithubRepositoryLocalImpl(private val githubDao: GithubDao): GithubRepository {
    override suspend fun listRepos(
        language: String,
        sortReposBy: String,
        currentPage: Int
    ): DataState<Repositories?> {
        return withContext(Dispatchers.Default) {
            try {
                val repositories = mutableListOf<Repository>()
                githubDao.getRepositories().forEach {
                    repositories.add( it.toRepository())
                }

                DataState.Success<Repositories?>(Repositories(repositories, repositories.size))
            } catch(e: Exception) {
                DataState.Error<Repositories?>(null, e)
            }
        }
    }

    override suspend fun saveRepos(repositories: Repositories) {
        repositories.repositoryList.forEach { currentRepository ->
            val repository = currentRepository.toEntity()
            githubDao.addRepository(repository)
        }
    }

    override suspend fun saveCurrentPage(pageNum: Int) {
        githubDao.addPage(Page(pageNum).toEntity())
    }

    override suspend fun getRepositoriesCount(): DataState<Int> {
        return withContext(Dispatchers.Default) {
            try {
                DataState.Success<Int>(githubDao.getRepositoriesCount())
            } catch(e: Exception) {
                DataState.Error<Int>(null, null)
            }
        }
    }
}