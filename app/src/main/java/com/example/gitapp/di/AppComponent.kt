package com.example.gitapp.di

import com.example.gitapp.framework.GithubRepository
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface AppComponent {
    fun getRepo(): GithubRepository
}
