package com.example.auth

import androidx.lifecycle.ViewModel
import com.example.gitapp.framework.GithubRepository
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val Repository: GithubRepository) : ViewModel() {

    val user = Repository.user

    fun autintificate(token: String, Login: String) {
        Repository.autentificate(token, Login)
    }

    override fun onCleared() {
        super.onCleared()
        Repository.disposiables.clear()
    }
}
