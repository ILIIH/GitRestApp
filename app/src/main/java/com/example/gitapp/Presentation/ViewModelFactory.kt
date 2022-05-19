package com.example.gitapp.Presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.core.Data.GithubRepository
import com.example.gitapp.Presentation.Login.LoginViewModel
import javax.inject.Inject


class ViewModelFactory @Inject constructor(private val Repository: GithubRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginViewModel(Repository) as T
    }
}