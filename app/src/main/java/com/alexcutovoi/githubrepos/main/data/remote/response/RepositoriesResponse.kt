package com.alexcutovoi.githubrepos.main.data.remote.response

import com.alexcutovoi.githubrepos.main.domain.model.Repositories
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