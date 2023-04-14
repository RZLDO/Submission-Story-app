package com.example.storyappsubmission

import androidx.lifecycle.ViewModel
import com.example.storyappsubmission.data.local.UserModel
import com.example.storyappsubmission.data.local.UserPreferences

class UserPreferencesViewModel(private val userPreferences: UserPreferences) : ViewModel(){
    fun saveUserData(userModel: UserModel){
        userPreferences.saveUser(userModel)
    }

    fun loadUserData():UserModel?{
        return userPreferences.getUser()
    }
    fun clearUserData(){
        userPreferences.clearUser()
    }
}