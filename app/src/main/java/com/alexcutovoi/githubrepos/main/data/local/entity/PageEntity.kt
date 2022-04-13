package com.alexcutovoi.githubrepos.main.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alexcutovoi.githubrepos.main.domain.model.Page

@Entity(tableName = "page")
data class PageEntity (
    @PrimaryKey(autoGenerate = true)
    val pageId: Int = 0,
    var pageNumber: Int = 0
)

fun PageEntity.toPage(): Page {
    return Page(pageNumber = this.pageNumber)
}