package com.example.postlist.di

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.postlist.R
import com.example.postlist.data.PostDao
import com.example.postlist.data.PostsDatabase
import com.example.postlist.network.PostApi
import com.example.postlist.other.Constants.BASE_URL
import com.example.postlist.other.Constants.POSTS_DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providePostsDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        PostsDatabase::class.java,
        POSTS_DATABASE_NAME
    ).build()


    @Singleton
    @Provides
    fun providePostDao(
        database: PostsDatabase
    ) = database.getPostDao()

    @Singleton
    @Provides
    fun provideApi(): PostApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(PostApi::class.java)
    }

    @Singleton
    @Provides
    fun provideGlideInstance(
        @ApplicationContext context: Context
    ) = Glide.with(context).setDefaultRequestOptions(
        RequestOptions()
            .placeholder(R.drawable.ic_image)
            .error(R.drawable.ic_image)
    )
}