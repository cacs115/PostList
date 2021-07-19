package com.example.postlist.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [Post::class],
    version = 1
)

abstract class PostsDatabase : RoomDatabase() {

    abstract fun getPostDao(): PostDao
}