package com.alexcutovoi.githubrepos.login.domain.model

data class Repositories (
    val repositoryList: List<Repository>,
    val totalCount: Int
)