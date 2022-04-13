package com.alexcutovoi.githubrepos.main.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.alexcutovoi.githubrepos.main.data.local.entity.convertes.LicenseConverter
import com.alexcutovoi.githubrepos.main.domain.model.Repository

@Entity(tableName = "repository")
data class RepositoryEntity(
    @PrimaryKey
    val repositoryid: Int = 0,
    val repositoryUrl: String,
    val description: String?,
    val forks: Int,
    val forksCount: Int,
    val itemUrl: String,
    @Embedded
    val license: LicenseEntity?,
    val repositoryName: String,
    @Embedded
    val owner: OwnerEntity,
    val stargazersCount: Int
)

fun RepositoryEntity.toRepository(): Repository {
    return Repository(
        id = repositoryid,
        repositoryUrl = repositoryUrl,
        description = description ?: "No descripton",
        forks = forks,
        forksCount = forksCount,
        itemUrl = itemUrl,
        license = license?.toLicense(),
        repositoryName = repositoryName ?: "",
        owner = owner.toOwner(),
        stargazersCount = stargazersCount
    )
}