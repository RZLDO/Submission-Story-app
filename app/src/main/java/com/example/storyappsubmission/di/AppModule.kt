package com.example.storyappsubmission.di

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.storyappsubmission.data.local.UserPreferences.Companion.USER_TOKEN
import com.example.storyappsubmission.data.login.remote.LoginService
import com.example.storyappsubmission.data.register.remote.RegisterService
import com.example.storyappsubmission.data.story.remote.StoryService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val base_url = "https://story-api.dicoding.dev/v1/"

val sharePreferencesModule = module {
    single { get<Context>().getSharedPreferences("sdasd",Context.MODE_PRIVATE) }
}
val networkModule = module {
    single {
        OkHttpClient().newBuilder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor { chain ->
                val requestBuilder = chain.request().newBuilder()
                val token = get<SharedPreferences>().getString(USER_TOKEN,"")
                Log.d("cekTokenInRetrofit", "$token")
                if (!token.isNullOrEmpty()){
                    requestBuilder.addHeader("Authorization", "Bearer $token")
                }
                chain.proceed(requestBuilder.build())
            }
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
    single { provideStoryService(get()) }
}

fun provideLoginService(retrofit: Retrofit): LoginService =
    retrofit.create(LoginService::class.java)

fun provideRegisterService(retrofit: Retrofit): RegisterService =
    retrofit.create(RegisterService::class.java)

fun provideStoryService(retrofit: Retrofit): StoryService =
    retrofit.create(StoryService::class.java)