package com.example.storyappsubmission.data.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.storyappsubmission.data.login.model.LoginResponse
import com.example.storyappsubmission.data.login.model.LoginResult
import com.example.storyappsubmission.data.login.remote.LoginService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginRepository(private val loginService: LoginService) {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean>
        get() = _isLoading

     fun userLogin(email:String, password:String):LiveData<LoginResult>{
        _isLoading.value = true
        val liveData = MutableLiveData<LoginResult>()
        loginService.userLogin(email,password).enqueue(object : Callback<LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                _isLoading.value = false
                if (response.isSuccessful){
                    Log.d("loginRepository", response.body().toString())
                    liveData.value = response.body()?.loginResult
                }else{
                    Log.d("loginRepository","onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _isLoading.value = false
                Log.d("loginRepository","onFailure : ${t.message}")
            }
        })
        return liveData
    }
}