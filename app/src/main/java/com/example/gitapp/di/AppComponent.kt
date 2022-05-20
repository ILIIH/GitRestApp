package com.example.gitapp.di

import android.content.Context
import com.example.gitapp.Presentation.Login.LoginFragment
import com.example.gitapp.Presentation.Profile.ProfileFragment
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

    fun LoginInject(app: LoginFragment)
    fun ProfileInject(app: ProfileFragment)
}