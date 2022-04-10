package com.alexcutovoi.githubrepos.login.data.repository

import com.alexcutovoi.githubrepos.login.domain.model.Repositories

interface GithubRepository{
    suspend fun listRepos(language: String, sortReposBy: String, currentPage: Int) : Repositories?
}