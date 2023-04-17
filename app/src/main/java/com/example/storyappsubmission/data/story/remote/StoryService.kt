package com.example.storyappsubmission.data.story.remote

import com.example.storyappsubmission.data.story.model.DetailStoryResponse
import com.example.storyappsubmission.data.story.model.FetchStoryResponse
import com.example.storyappsubmission.data.story.model.ListStoryItem
import com.example.storyappsubmission.data.story.model.StoryResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface StoryService {
    @Multipart
    @POST("stories")
    fun addStory(
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody
    ): Call<StoryResponse>

    @GET("stories")
    fun getAllStories():Call<FetchStoryResponse>

    @GET("stories/{id}")
    fun getDetailStory(@Path("id")id:String):Call<DetailStoryResponse>

}