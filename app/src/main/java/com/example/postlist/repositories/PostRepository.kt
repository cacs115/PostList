package com.example.postlist.repositories

import androidx.lifecycle.LiveData
import com.example.postlist.data.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {

    suspend fun insertPost(post: Post)

    suspend fun insertPosts(posts: List<Post>)

    suspend fun updatePost(post: Post)

    fun deleteAll()

    fun getAllPosts(): Flow<List<Post>>
}