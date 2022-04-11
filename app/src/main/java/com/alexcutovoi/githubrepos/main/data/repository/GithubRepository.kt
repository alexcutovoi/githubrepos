package com.alexcutovoi.githubrepos.main.data.repository

import com.alexcutovoi.githubrepos.common.DataState
import com.alexcutovoi.githubrepos.main.domain.model.Repositories

interface GithubRepository{
    suspend fun listRepos(language: String, sortReposBy: String, currentPage: Int) : DataState<Repositories?>
}