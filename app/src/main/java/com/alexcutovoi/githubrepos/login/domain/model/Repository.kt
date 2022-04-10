package com.alexcutovoi.githubrepos.login.domain.model

import com.google.gson.annotations.SerializedName

data class Repository(
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