package com.example.profile.di
import com.example.gitapp.di.AppComponent
import com.example.profile.profile.ProfileVIewModelFactory
import com.example.profile.profile.ProfileViewModel
import dagger.Component

@ProfileScope
@Component(dependencies = [AppComponent::class])
interface ProfileComponent {
    fun viewModelsFactory(): ProfileVIewModelFactory
}
