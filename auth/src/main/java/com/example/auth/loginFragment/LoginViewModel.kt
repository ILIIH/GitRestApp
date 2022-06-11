package com.example.auth.loginFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.core.domain.User
import com.example.core.domain.helpers.ErrorEntity
import com.example.core.domain.helpers.Result
import com.example.gitapp.framework.GithubRepository
import com.example.gitapp.util.BaseViewModel
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val Repository: GithubRepository) : BaseViewModel() {

    private var _user = MutableLiveData<Result<User>>()
    val user: LiveData<Result<User>>
        get() = _user

    fun autintificate(token: String, Login: String) {

        if (Login.length < 4 || token.length < 8) {
            _user.postValue(Result.Error(ErrorEntity.Credentials))
            return
        }

        val disposiable = Repository.authenticate(token).subscribe(
            { next_item ->
                if (next_item.login == Login)_user.postValue(Result.Success(next_item))
                else _user.postValue(Result.Error(ErrorEntity.Credentials))
            }
        ) { Error ->
            Error.message
            if (Error.message.equals("401"))
                _user.postValue(
                    Result.Error(
                        ErrorEntity.Network
                    )
                )
            else _user.postValue(
                Result.Error(
                    ErrorEntity.Credentials
                )
            )
        }

        addDisposable { disposiable }
    }
}
