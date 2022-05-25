package com.example.gitapp.di

import android.content.Context
import com.example.gitapp.presentation.login.LoginFragment
import com.example.gitapp.presentation.profile.ProfileFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun loginInject(app: LoginFragment)
    fun profileInject(app: ProfileFragment)
}
