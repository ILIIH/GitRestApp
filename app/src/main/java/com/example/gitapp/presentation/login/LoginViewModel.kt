package com.example.gitapp.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.core.Domain.User
import com.example.core.Domain.helpers.Result
import com.example.gitapp.framework.GithubRepository
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val Repository: GithubRepository) : ViewModel() {

    var disposiables: CompositeDisposable = Repository.disposables
    private var _user = Repository.user

    val user: LiveData<Result<User>>
        get() = _user

    fun autintificate(token: String, Login: String) {

        Repository.autentificate(token, Login)
    }

    override fun onCleared() {
        super.onCleared()
        disposiables.clear()
    }
}
