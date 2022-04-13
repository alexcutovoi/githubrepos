package com.alexcutovoi.githubrepos

import android.app.Application
import android.content.Context

class GithubApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        APP_CONTEXT = applicationContext
    }

    companion object {
        private lateinit var APP_CONTEXT: Context

        fun getApplicationContext(): Context {
            return GithubApplication.APP_CONTEXT
        }
    }
}