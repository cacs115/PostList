package com.example.postlist.ui.viewmodels

import androidx.lifecycle.*
import com.example.postlist.data.Post
import com.example.postlist.other.Resource
import com.example.postlist.repositories.DefaultPostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    val repository: DefaultPostRepository
) : ViewModel() {

    private val postsList = repository.getAllPosts().asLiveData()

    val posts = MediatorLiveData<Resource<List<Post>>>()

    init {
        posts.addSource(postsList) { result ->
            result.let { posts.value = it!! }
        }
    }

    fun insertPost(post: Post) = viewModelScope.launch {
        repository.insertPost(post)
    }

    fun updatePost(post: Post) = viewModelScope.launch {
        repository.updatePost(post)
    }

    fun deletePost(post: Post) = viewModelScope.launch {
        repository.deletePost(post)
    }

    fun deleteAllPosts() = viewModelScope.launch {
        repository.deleteAll()
    }

    fun getAllPosts() = postsList.value?.let { posts.value = it }
}