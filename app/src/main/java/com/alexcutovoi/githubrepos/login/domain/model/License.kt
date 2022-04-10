package com.alexcutovoi.githubrepos.login.domain.model

import com.google.gson.annotations.SerializedName

data class License(
    @SerializedName("name")
    val licenseName: String,
    @SerializedName("url")
    val licenseUrl: String
)