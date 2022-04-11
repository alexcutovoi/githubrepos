package com.alexcutovoi.githubrepos.main.data.repository

import com.alexcutovoi.githubrepos.BuildConfig
import com.alexcutovoi.githubrepos.common.DataState
import com.alexcutovoi.githubrepos.common.utils.Constants
import com.alexcutovoi.githubrepos.main.data.remote.GithubApi
import com.alexcutovoi.githubrepos.main.data.remote.HttpClient
import com.alexcutovoi.githubrepos.main.data.remote.response.ErrorResponse
import com.alexcutovoi.githubrepos.main.data.remote.response.toRepositories
import com.alexcutovoi.githubrepos.main.domain.model.Repositories
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class GithubRepositoryImpl(private val httpClient: HttpClient) : GithubRepository{
    private var githubApi: GithubApi

    init {
        httpClient.create(Constants.GITHUB_BASE_URL)
        githubApi = httpClient.createService(GithubApi::class.java)
    }
    override suspend fun listRepos(language: String, sortReposBy: String, currentPage: Int): DataState<Repositories?> {
        return withContext(Dispatchers.Default){
            try {
                val authToken =
                    if (BuildConfig.DEV_PAT_TOKEN.isNotEmpty()) Constants.AUTH_TOKEN_PREFIX + BuildConfig.DEV_PAT_TOKEN else ""

                val response = githubApi.listRepos(
                    authToken,
                    language,
                    sortReposBy,
                    currentPage
                )

                if(response.isSuccessful)
                    DataState.Success<Repositories?>(response.body()!!.toRepositories())
                else {
                    val errorResponse = Gson().fromJson(response.errorBody()!!.string(), ErrorResponse::class.java)
                    DataState.Error<Repositories?>(null, Exception(errorResponse.message))
                }
            } catch (e: HttpException){
                DataState.Error<Repositories?>(null, e)
            }
        }
    }
}