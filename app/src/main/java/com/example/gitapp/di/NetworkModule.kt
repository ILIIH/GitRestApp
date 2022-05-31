package com.example.gitapp.di

import com.example.gitapp.BuildConfig
import com.example.gitapp.framework.network.GithubService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
object NetworkModule {
    @Singleton
    @JvmStatic
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BaceURL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Singleton
    @JvmStatic
    @Provides
    fun provideApi(retrofit: Retrofit): GithubService {
        return retrofit.create(GithubService::class.java)
    }
}
