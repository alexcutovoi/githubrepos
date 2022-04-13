package com.alexcutovoi.githubrepos.main.data.local

import androidx.room.*
import com.alexcutovoi.githubrepos.GithubApplication
import com.alexcutovoi.githubrepos.common.utils.Constants
import com.alexcutovoi.githubrepos.main.data.local.entity.LicenseEntity
import com.alexcutovoi.githubrepos.main.data.local.entity.OwnerEntity
import com.alexcutovoi.githubrepos.main.data.local.entity.PageEntity
import com.alexcutovoi.githubrepos.main.data.local.entity.RepositoryEntity

@Database(entities = [LicenseEntity::class, OwnerEntity::class,
    RepositoryEntity::class, PageEntity::class], version = 1)
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