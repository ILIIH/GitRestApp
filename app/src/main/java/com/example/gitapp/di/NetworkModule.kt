package com.example.gitapp.di

import com.example.core.Data.GithubService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
abstract  class NetworkModule {
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("YOUR_BASE_URL")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()


    }

    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit): GithubService {
        return retrofit.create(GithubService::class.java)
    }
}