package com.example.postlist.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.withTransaction
import com.example.postlist.data.Post
import com.example.postlist.data.PostDao
import com.example.postlist.data.PostsDatabase
import com.example.postlist.data.User
import com.example.postlist.network.PostApi
import com.example.postlist.other.Resource
import com.example.postlist.other.networkBoundResource
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class DefaultPostRepository @Inject constructor(
    private val db: PostsDatabase,
    private val postApi: PostApi
) {
    private val postDao = db.getPostDao()

    suspend fun insertPost(post: Post) {
        postDao.insertPost(post)
    }

    suspend fun insertPosts(posts: List<Post>) {
        postDao.insertPosts(posts)
    }

    suspend fun deletePost(post: Post) {
        postDao.deletePost(post)
    }

    suspend fun updatePost(post: Post) {
        postDao.updatePost(post)
    }

    suspend fun deleteAll() {
        db.withTransaction {
            postDao.deleteAll()
        }
    }

    fun getAllPosts() = networkBoundResource(
        query = {
            postDao.getAllPosts()
        },
        fetch = {
            postApi.getPosts()
        },
        saveFetchResult = {
            db.withTransaction {
                postDao.deleteAll()
                postDao.insertPosts(it.body()!!)
            }
        }
    )

    suspend fun getAllPostsRemotely(): Resource<List<Post>> {
        return try {
            val response = postApi.getPosts()
            if(response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("An unknown error occured", null)
            } else {
                Resource.error("An unknown error occured", null)
            }
        } catch(e: Exception) {
            Resource.error("Couldn't reach the server. Check your internet connection", null)
        }
    }

    suspend fun getUser(userId: String): Resource<User> {
        return try {
            val response = postApi.getUser(userId)
            if(response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("An unknown error occured", null)
            } else {
                Resource.error("An unknown error occured", null)
            }
        } catch(e: Exception) {
            Resource.error("Couldn't reach the server. Check your internet connection", null)
        }
    }
}