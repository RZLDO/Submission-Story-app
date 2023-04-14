package com.example.storyappsubmission.presentation.register

import android.provider.ContactsContract.CommonDataKinds.Email
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.storyappsubmission.data.register.RegisterRepository
import com.example.storyappsubmission.data.register.model.RegisterResponse

class RegisterViewModel(private val registerRepository: RegisterRepository) : ViewModel(){
    private val _resultRegister = MutableLiveData<RegisterResponse>()
    val resultRegister : LiveData<RegisterResponse>
        get() = _resultRegister

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean>
        get() = _isLoading

    fun userRegister(nama:String, email: String, password:String){
        registerRepository.registerUsers(nama,email,password).observeForever {
            _resultRegister.value = it
        }
        registerRepository.isLoading.observeForever {
            _isLoading.value = it
        }
    }

}