package com.alexcutovoi.githubrepos.main.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alexcutovoi.githubrepos.main.domain.model.License

@Entity(tableName = "repository_license")
data class LicenseEntity(
    @PrimaryKey(autoGenerate = true) val licenseId: Int = 0,
    val licenseName: String,
    val licenseUrl: String?
)

fun LicenseEntity.toLicense(): License {
    return License(licenseName = licenseName, licenseUrl = licenseUrl ?: "")
}