package com.alexcutovoi.githubrepos.main.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alexcutovoi.githubrepos.main.data.local.entity.LicenseEntity
import com.alexcutovoi.githubrepos.main.data.local.entity.OwnerEntity
import com.alexcutovoi.githubrepos.main.data.local.entity.RepositoryEntity

@Dao
interface GithubDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addLicense(license: LicenseEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addOwner(owner: OwnerEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addRepository(repository: RepositoryEntity)

    @Query("SELECT * FROM repository")
    fun getRepositories(): List<RepositoryEntity>

    @Query("SELECT COUNT(*) FROM repository")
    fun getRepositoriesCount(): Int
}