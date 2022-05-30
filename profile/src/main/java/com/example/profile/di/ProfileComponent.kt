package com.example.profile.di
import com.example.gitapp.di.AppComponent
import com.example.profile.profile.profileFragment.ProfileVIewModelFactory
import dagger.Component

@ProfileScope
@Component(dependencies = [AppComponent::class])
interface ProfileComponent {
    fun viewModelsFactory(): ProfileVIewModelFactory
}
