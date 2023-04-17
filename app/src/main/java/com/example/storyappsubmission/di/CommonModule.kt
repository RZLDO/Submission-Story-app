package com.example.storyappsubmission.di

import com.example.storyappsubmission.UserPreferencesViewModel
import com.example.storyappsubmission.data.local.UserPreferences
import com.example.storyappsubmission.data.login.LoginRepository
import com.example.storyappsubmission.data.register.RegisterRepository
import com.example.storyappsubmission.data.story.StoryRepository
import com.example.storyappsubmission.presentation.addStory.AddStoryViewModel
import com.example.storyappsubmission.presentation.detail.DetailViewModel
import com.example.storyappsubmission.presentation.home.HomeViewModel
import com.example.storyappsubmission.presentation.login.LoginViewModel
import com.example.storyappsubmission.presentation.register.RegisterViewModel
import org.koin.dsl.module

val repositoryModule = module {
    single { LoginRepository(get()) }
    single { RegisterRepository(get()) }
    single { StoryRepository(get()) }

}

val viewModelModule = module {
    single { LoginViewModel(get()) }
    single { RegisterViewModel(get()) }
    single { AddStoryViewModel(get()) }
    single { UserPreferencesViewModel(get()) }
    single { HomeViewModel(get()) }
    single { DetailViewModel(get()) }

}

val userManageModule = module {
    single { UserPreferences(get()) }
}