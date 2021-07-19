package com.example.postlist.data

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPost(post: Post)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPosts(posts: List<Post>)

    @Delete
    suspend fun deletePost(post: Post)

    @Update
    suspend fun updatePost(post: Post)

    @Query("DELETE FROM posts_table")
    fun deleteAll()

    @Query("SELECT * FROM posts_table")
    fun getAllPosts(): Flow<List<Post>>
}