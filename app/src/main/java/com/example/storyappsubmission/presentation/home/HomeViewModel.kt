package com.example.storyappsubmission.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.storyappsubmission.data.story.StoryRepository
import com.example.storyappsubmission.data.story.model.ListStoryItem

class HomeViewModel(private val storyRepository: StoryRepository):ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean>
        get() = _isLoading

    private val _fetchStory = MutableLiveData<List<ListStoryItem>>()
    val fetchStory : LiveData<List<ListStoryItem>>
        get() = _fetchStory

    fun fetchStory(){
        storyRepository.fetchStory().observeForever {
            _fetchStory.value = it
        }
        storyRepository.isLoading.observeForever {
            _isLoading.value = it
        }
    }
}