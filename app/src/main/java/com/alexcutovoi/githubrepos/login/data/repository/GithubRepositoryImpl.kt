package com.alexcutovoi.githubrepos.login.data.repository

import com.alexcutovoi.githubrepos.BuildConfig
import com.alexcutovoi.githubrepos.common.utils.Constants
import com.alexcutovoi.githubrepos.login.data.remote.GithubApi
import com.alexcutovoi.githubrepos.login.data.remote.HttpClient
import com.alexcutovoi.githubrepos.login.data.remote.response.RepositoriesResponse
import com.alexcutovoi.githubrepos.login.data.remote.response.toRepositories
import com.alexcutovoi.githubrepos.login.domain.model.Repositories
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response

class GithubRepositoryImpl(private val httpClient: HttpClient) : GithubRepository{
    private var githubApi: GithubApi

    init {
        httpClient.create(Constants.GITHUB_BASE_URL)
        githubApi = httpClient.createService(GithubApi::class.java)
    }
    override suspend fun listRepos(language: String, sortReposBy: String, currentPage: Int): Repositories? {
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
                    response.body()!!.toRepositories()
                else {
                    null
                }
            } catch (e: HttpException){
                null
            }
        }
    }
}