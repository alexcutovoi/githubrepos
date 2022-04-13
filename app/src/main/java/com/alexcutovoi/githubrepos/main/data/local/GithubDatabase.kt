package com.alexcutovoi.githubrepos.main.data.local

import android.content.Context
import androidx.room.*
import com.alexcutovoi.githubrepos.GithubApplication
import com.alexcutovoi.githubrepos.common.utils.Constants
import com.alexcutovoi.githubrepos.main.data.local.entity.LicenseEntity
import com.alexcutovoi.githubrepos.main.data.local.entity.OwnerEntity
import com.alexcutovoi.githubrepos.main.data.local.entity.RepositoryEntity
import com.alexcutovoi.githubrepos.main.data.local.entity.convertes.LicenseConverter

@Database(entities = [LicenseEntity::class, OwnerEntity::class,
    RepositoryEntity::class], version = 1)
@TypeConverters(LicenseConverter::class)
abstract class GithubDatabase : RoomDatabase(){
    abstract fun getGithubDao(): GithubDao

    companion object {
        @Volatile private var INSTANCE: GithubDatabase? = null

        fun getDb(): GithubDatabase {
            val context = GithubApplication.getApplicationContext()
            var tmp = INSTANCE
            return tmp
                ?: synchronized(this) {
                    tmp = Room.databaseBuilder(
                        context.applicationContext,
                        GithubDatabase::class.java,
                        Constants.DATABASE_NAME
                    ).build()

                    INSTANCE = tmp
                    tmp!!
                }
        }
    }
}