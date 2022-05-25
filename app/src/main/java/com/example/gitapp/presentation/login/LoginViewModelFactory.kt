package com.example.gitapp.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gitapp.framework.GithubRepository
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class LoginViewModelFactory @Inject constructor(private val Repository: GithubRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginViewModel(Repository) as T
    }
}
