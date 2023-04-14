package com.example.storyappsubmission.data.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.storyappsubmission.data.login.model.LoginResult
import com.example.storyappsubmission.data.login.remote.LoginService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginRepository(private val loginService: LoginService) {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean>
        get() = _isLoading

     fun userLogin(email:String, password:String):LiveData<LoginResult?>{
        _isLoading.value = true
        val liveData = MutableLiveData<LoginResult>()
        loginService.userLogin(email,password).enqueue(object : Callback<LoginResult?>{
            override fun onResponse(call: Call<LoginResult?>, response: Response<LoginResult?>) {
                if (response.isSuccessful){
                    _isLoading.value = false
                    Log.d("loginRepository", response.body().toString())
                    liveData.value = response.body()
                }
            }

            override fun onFailure(call: Call<LoginResult?>, t: Throwable) {
                _isLoading.value = false
                Log.d("loginRepository", t.toString())
            }

        })
        return liveData
    }
}