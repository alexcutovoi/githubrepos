package com.alexcutovoi.githubrepos.login.data.remote

import com.alexcutovoi.githubrepos.login.data.remote.response.RepositoriesResponse
import retrofit2.Response

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface GithubApi{

    @GET("search/repositories")
    suspend fun listRepos(
        //@Header("Authorization") token: String,
        @Query("q")language: String,
        @Query("sort")sortReposBy: String,
        @Query("page")currentPage: Int
    ) : Response<RepositoriesResponse>
}