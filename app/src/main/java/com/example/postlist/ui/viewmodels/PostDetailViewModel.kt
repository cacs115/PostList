package com.example.postlist.ui.viewmodels

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.postlist.data.User
import com.example.postlist.other.Resource
import com.example.postlist.repositories.DefaultPostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostDetailViewModel @Inject constructor(
    val repository: DefaultPostRepository
) : ViewModel() {

     val user = MediatorLiveData<Resource<User>>()

    fun getUser(userId: String) = viewModelScope.launch {
        repository.getUser(userId).also { user.value = it }
    }


}