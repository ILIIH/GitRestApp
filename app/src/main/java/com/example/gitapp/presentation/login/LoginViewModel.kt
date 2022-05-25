package com.example.gitapp.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.core.Domain.User
import com.example.core.Domain.helpers.Result
import com.example.gitapp.framework.GithubRepository

class LoginViewModel(private val Repository: GithubRepository) : ViewModel() {

    private var _user = Repository.user
    val user: LiveData<Result<User>>
        get() = _user

    fun autintificate(token: String, Login: String) {

        Repository.autentificate(token, Login)
    }
}
