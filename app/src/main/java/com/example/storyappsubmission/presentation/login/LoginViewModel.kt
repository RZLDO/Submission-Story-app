package com.example.storyappsubmission.presentation.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.storyappsubmission.data.login.LoginRepository
import com.example.storyappsubmission.data.login.model.LoginResponse
import com.example.storyappsubmission.data.login.model.LoginResult

class LoginViewModel(private val loginRepository: LoginRepository) :ViewModel(){
    private val _loginResult = MutableLiveData<LoginResult?>()
    val loginResult : LiveData<LoginResult?>
        get() = _loginResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun userLogin(email:String, password:String){
        loginRepository.userLogin(email, password).observeForever{
            _loginResult.value = it
            Log.d("LoginViewModel", it.toString())
        }
        loginRepository.isLoading.observeForever {
            _isLoading.value = it
        }
    }
}