package com.alexcutovoi.githubrepos.login.data.remote.response

import com.alexcutovoi.githubrepos.login.domain.model.License

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