package com.example.gitapp.Presentation.Login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.core.Data.GithubRepository
import javax.inject.Inject

class LoginViewModelFactory @Inject constructor(private val Repository: GithubRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginViewModel(Repository) as T
    }
}