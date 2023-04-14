package com.example.storyappsubmission.di

import android.content.Context
import com.example.storyappsubmission.data.login.remote.LoginService
import com.example.storyappsubmission.data.register.remote.RegisterService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val base_url = "https://story-api.dicoding.dev/v1/"

val sharePreferencesModule = module {
    single { get<Context>().getSharedPreferences("user_preferences",Context.MODE_PRIVATE) }
}
val networkModule = module {
    single {
        OkHttpClient().newBuilder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    single {
        Retrofit.Builder()
            .client(get())
            .baseUrl(base_url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single { provideLoginService(get()) }
    single { provideRegisterService(get()) }
}

fun provideLoginService(retrofit: Retrofit): LoginService =
    retrofit.create(LoginService::class.java)

fun provideRegisterService(retrofit: Retrofit): RegisterService =
    retrofit.create(RegisterService::class.java)