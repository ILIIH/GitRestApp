package com.example.gitapp.di

import com.example.core.data.GitRepository
import com.example.gitapp.framework.GithubRepository
import dagger.Binds
import dagger.Module

@Module
abstract class RepoBindingModule {
    @Binds
    abstract fun bindRepo(repo: GithubRepository): GitRepository
}
