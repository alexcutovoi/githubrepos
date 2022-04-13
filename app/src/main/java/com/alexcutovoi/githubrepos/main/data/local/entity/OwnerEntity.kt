package com.alexcutovoi.githubrepos.main.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alexcutovoi.githubrepos.main.domain.model.Owner

@Entity(tableName = "repository_owner")
data class OwnerEntity(
    @PrimaryKey val ownerId: Int = 0,
    val avatarImageUrl: String,
    val ownerUrl: String,
    val name: String
)

fun OwnerEntity.toOwner(): Owner {
    return Owner(id = ownerId, avatarImageUrl = avatarImageUrl, ownerUrl = ownerUrl, name = name)
}