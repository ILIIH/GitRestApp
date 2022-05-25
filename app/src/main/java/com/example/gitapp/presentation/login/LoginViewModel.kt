package com.example.gitapp.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.core.Domain.User
import com.example.core.Domain.helpers.ErrorEntity
import com.example.core.Domain.helpers.Result
import com.example.gitapp.framework.GithubRepository
import io.reactivex.schedulers.Schedulers

class LoginViewModel(private val Repository: GithubRepository) : ViewModel() {

    private var _user = MutableLiveData<Result<User>>()
    val user: LiveData<Result<User>>
        get() = _user

    fun autintificate(token: String, Login: String) {

        if (Login.length < 4 || token.length < 8) {
            _user.postValue(Result.Error(ErrorEntity.Credentials))
            return
        }

        Repository.Autentificate(token)
            .subscribeOn(Schedulers.io())
            .subscribe(
                { next_item ->
                    if (next_item.login == Login)_user.postValue(Result.Success(next_item))
                    else _user.postValue(Result.Error(ErrorEntity.Credentials))
                },
                { Error ->
                    Error.message; if (Error.message.equals("HTTP 401"))_user.postValue(Result.Error(ErrorEntity.Credentials))
                    else if (Error.message.equals("Unable to resolve host "))_user.postValue(Result.Error(ErrorEntity.Network))
                }
            )
    }
}
