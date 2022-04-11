package com.alexcutovoi.githubrepos.main.domain.model

data class Repositories (
    val repositoryList: List<Repository>,
    val totalCount: Int
)