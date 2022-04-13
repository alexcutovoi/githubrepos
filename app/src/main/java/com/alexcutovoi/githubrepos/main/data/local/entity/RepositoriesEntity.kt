package com.alexcutovoi.githubrepos.main.data.local.entity

import androidx.room.Entity

//@Entity(tableName = "repositories")
data class RepositoriesEntity (
    val repositoryList: List<RepositoryEntity>,
    val totalCount: Int
)