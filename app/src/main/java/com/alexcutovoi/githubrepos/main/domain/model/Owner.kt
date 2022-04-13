package com.alexcutovoi.githubrepos.main.domain.model

import com.alexcutovoi.githubrepos.main.data.local.entity.OwnerEntity

data class Owner(
    val id: Int,
    val avatarImageUrl: String,
    val ownerUrl: String,
    val name: String
)

fun Owner.toEntity(): OwnerEntity {
    return OwnerEntity(
        ownerId = this.id,
        avatarImageUrl = this.avatarImageUrl,
        ownerUrl = this.ownerUrl,
        name = this.name
    )
}