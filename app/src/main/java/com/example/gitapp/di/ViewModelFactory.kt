package com.example.gitapp.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gitapp.presentation.login.LoginViewModel
import com.example.gitapp.presentation.profile.ProfileViewModel
import javax.inject.Inject
import javax.inject.Provider

class ViewModelFactory @Inject constructor(
    LoginModelProvider: Provider<LoginViewModel>,
    ProfileModelProvider: Provider<ProfileViewModel>
) : ViewModelProvider.Factory {

    private val providers = mapOf<Class<*>, Provider<out ViewModel>>(
        LoginViewModel::class.java to LoginModelProvider,
        ProfileViewModel::class.java to ProfileModelProvider
    )

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return providers[modelClass]!!.get() as T
    }
}
