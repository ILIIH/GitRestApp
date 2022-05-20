package com.example.gitapp.Presentation.Profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.core.Data.GithubRepository
import com.example.gitapp.Presentation.Login.LoginViewModel
import javax.inject.Inject

class ProfileViewModelFactory @Inject constructor(private val Repository: GithubRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProfileViewModel(Repository) as T
    }
}