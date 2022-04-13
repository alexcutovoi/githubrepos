package com.alexcutovoi.githubrepos.main.domain.model

import com.alexcutovoi.githubrepos.main.data.local.entity.PageEntity

data class Page (
    val pageNumber: Int = 0
)

fun Page.toEntity(): PageEntity {
    return PageEntity(
        pageNumber = this.pageNumber
    )
}