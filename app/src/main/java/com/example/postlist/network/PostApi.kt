package com.example.postlist.network

import com.example.postlist.data.Post
import com.example.postlist.data.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PostApi {

    @GET("/posts")
    suspend fun getPosts(): Response<List<Post>>

    @GET("/users/")
    suspend fun getUser(
        @Query("q") userId: String
    ): Response<User>
}