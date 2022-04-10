package com.alexcutovoi.githubrepos.login.data.remote.response

import com.alexcutovoi.githubrepos.login.domain.model.Repository
import com.alexcutovoi.githubrepos.login.domain.model.Repositories
import com.google.gson.annotations.SerializedName

data class RepositoriesResponse(
    val incomplete_results: Boolean,
    @SerializedName("items")
    val itemsResponse: List<RepositoryResponse>,
    @SerializedName("total_count")
    val totalCount: Int
)

fun RepositoriesResponse.toRepositories(): Repositories {
    val list = itemsResponse.map { itemResponse -> itemResponse.toRepository() }
    return Repositories(repositoryList = list, totalCount = totalCount)
}