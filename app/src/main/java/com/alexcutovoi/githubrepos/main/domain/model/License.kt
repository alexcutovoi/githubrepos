package com.alexcutovoi.githubrepos.main.domain.model

import com.alexcutovoi.githubrepos.main.data.local.entity.LicenseEntity

data class License(
    val licenseName: String,
    val licenseUrl: String
)

fun License.toEntity(): LicenseEntity {
    return LicenseEntity(licenseName = this.licenseName, licenseUrl = this.licenseUrl)
}