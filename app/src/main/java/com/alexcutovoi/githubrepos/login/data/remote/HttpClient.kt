package com.alexcutovoi.githubrepos.login.data.remote

interface HttpClient {
    fun create(baseUrl: String)
    fun <T> createService(service: Class<T>): T
}