package com.alexcutovoi.githubrepos.main.data.remote.response

import com.alexcutovoi.githubrepos.main.domain.model.License

data class LicenseResponse(
    val key: String,
    val name: String,
    val node_id: String,
    val spdx_id: String,
    val url: String? = ""
)

fun LicenseResponse.toLicense() : License {
    return License(licenseName = name, licenseUrl = url ?: "")
}