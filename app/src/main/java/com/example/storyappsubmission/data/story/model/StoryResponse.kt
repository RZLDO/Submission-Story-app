package com.example.storyappsubmission.data.story.model

import com.google.gson.annotations.SerializedName

data class StoryResponse(

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)
