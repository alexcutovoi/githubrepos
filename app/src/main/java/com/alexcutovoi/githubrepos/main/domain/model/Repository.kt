package com.alexcutovoi.githubrepos.main.domain.model

import com.alexcutovoi.githubrepos.main.data.local.entity.RepositoryEntity
import com.google.gson.annotations.SerializedName

data class Repository(
    val id: Int,
    @SerializedName("clone_url")
    val repositoryUrl: String,
    val description: String,
    val forks: Int,
    @SerializedName("forks_count")
    val forksCount: Int,
    @SerializedName("html_url")
    val itemUrl: String,
    @SerializedName("license")
    val license: License?,
    val repositoryName: String,
    @SerializedName("Owner")
    val owner: Owner,
    @SerializedName("stargazers_count")
    val stargazersCount: Int
)

fun Repository.toEntity(): RepositoryEntity {
    return RepositoryEntity(
        repositoryid = this.id,
        repositoryUrl = this.repositoryUrl,
        description = this.description,
        forks = this.forks,
        forksCount = this.forksCount,
        itemUrl = this.itemUrl,
        license = this.license?.toEntity(),
        repositoryName = this.repositoryName,
        owner = this.owner.toEntity(),
        stargazersCount = this.stargazersCount
    )
}