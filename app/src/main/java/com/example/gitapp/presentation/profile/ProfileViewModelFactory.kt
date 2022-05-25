package com.example.gitapp.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gitapp.framework.GithubRepository
import javax.inject.Inject

class ProfileViewModelFactory @Inject constructor(private val Repository: GithubRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProfileViewModel(Repository) as T
    }
}
