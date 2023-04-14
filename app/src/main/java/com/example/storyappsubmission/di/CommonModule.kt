package com.example.storyappsubmission.di

import com.example.storyappsubmission.UserPreferencesViewModel
import com.example.storyappsubmission.data.local.UserPreferences
import com.example.storyappsubmission.data.login.LoginRepository
import com.example.storyappsubmission.data.register.RegisterRepository
import com.example.storyappsubmission.presentation.login.LoginViewModel
import com.example.storyappsubmission.presentation.register.RegisterViewModel
import org.koin.dsl.module

val repositoryModule = module {
    single { LoginRepository(get()) }
    single { RegisterRepository(get()) }
}

val viewModelModule = module {
    single { LoginViewModel(get()) }
    single { RegisterViewModel(get()) }
    single { UserPreferencesViewModel(get()) }
}

val userManageModule = module {
    single { UserPreferences(get()) }
}