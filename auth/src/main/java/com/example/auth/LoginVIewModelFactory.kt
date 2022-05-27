package com.example.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

class LoginVIewModelFactory @Inject constructor(
    LoginModelProvider: Provider<LoginViewModel>
) : ViewModelProvider.Factory {

    private val providers = mapOf<Class<*>, Provider<out ViewModel>>(
        com.example.auth.LoginViewModel::class.java to LoginModelProvider,
    )

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return providers[modelClass]!!.get() as T
    }
}
