package com.example.di

import com.example.auth.loginFragment.LoginVIewModelFactory
import com.example.gitapp.di.AppComponent
import dagger.Component

@LoginScope
@Component(dependencies = [AppComponent::class])
interface AuthComponent {
    fun viewModelsFactory(): LoginVIewModelFactory
}
