package com.alexcutovoi.githubrepos.main.data.repository

import com.alexcutovoi.githubrepos.common.DataState
import com.alexcutovoi.githubrepos.main.domain.model.License
import com.alexcutovoi.githubrepos.main.domain.model.Repositories

interface GithubRepository{
    suspend fun listRepos(language: String, sortReposBy: String, currentPage: Int) : DataState<Repositories?>
    suspend fun saveRepos(repositories: Repositories)
    suspend fun saveCurrentPage(pageNum: Int)
    suspend fun getRepositoriesCount(): DataState<Int>
}