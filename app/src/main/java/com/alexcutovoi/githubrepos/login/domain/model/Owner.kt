package com.alexcutovoi.githubrepos.login.domain.model

import com.google.gson.annotations.SerializedName

data class Owner(
    val avatarImageUrl: String,
    val ownerUrl: String,
    val name: String
)