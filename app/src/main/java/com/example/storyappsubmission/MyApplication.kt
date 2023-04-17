package com.example.storyappsubmission

import android.app.Application
import android.content.Context
import com.example.storyappsubmission.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        GlobalContext.startKoin{
            androidContext(this@MyApplication)
            modules(
                networkModule, repositoryModule, viewModelModule, userManageModule, sharePreferencesModule
            )
        }
    }
}