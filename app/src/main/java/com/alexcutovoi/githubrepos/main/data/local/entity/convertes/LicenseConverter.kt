package com.alexcutovoi.githubrepos.main.data.local.entity.convertes

import androidx.room.TypeConverter
import com.alexcutovoi.githubrepos.main.data.local.entity.LicenseEntity
import com.alexcutovoi.githubrepos.main.domain.model.License
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class LicenseConverter {
    @TypeConverter
    fun toLicenseObject(license: String?): LicenseEntity? {
        license?.let {
            val gson = Gson()
            val listType = object: TypeToken<License?>() {}.type
            return gson.fromJson(license, listType)
        }

        return null
    }

    @TypeConverter
    fun toLicenseString(license: License?): String? {
        license?.let {
            val gson = Gson()
            return gson.toJson(LicenseEntity(licenseName = license.licenseName, licenseUrl = license.licenseUrl))
        }
        return null
    }
}