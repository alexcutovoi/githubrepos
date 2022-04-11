package com.alexcutovoi.githubrepos.main.domain.model

import com.google.gson.annotations.SerializedName

data class License(
    @SerializedName("name")
    val licenseName: String,
    @SerializedName("url")
    val licenseUrl: String
)