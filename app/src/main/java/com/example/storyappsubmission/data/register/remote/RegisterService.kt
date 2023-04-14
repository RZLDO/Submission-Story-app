package com.example.storyappsubmission.data.register.remote

import android.provider.ContactsContract.CommonDataKinds.Email
import com.example.storyappsubmission.data.register.model.RegisterResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface RegisterService {
    @FormUrlEncoded
    @POST("register")
    fun userRegister(
        @Field("name")name:String,
        @Field("email")email: String,
        @Field("password")password:String
    ) : Call<RegisterResponse>
}