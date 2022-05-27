package com.example.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.core.domain.User
import com.example.core.domain.helpers.ErrorEntity
import com.example.core.domain.helpers.Result
import com.example.gitapp.framework.GithubRepository
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val Repository: GithubRepository) : ViewModel() {

    var disposiables: CompositeDisposable = CompositeDisposable()
    private var _user = MutableLiveData<Result<User>>()
    val user: LiveData<Result<User>>
        get() = _user

    fun autintificate(token: String, Login: String) {

        if (Login.length < 4 || token.length < 8) {
            _user.postValue(Result.Error(ErrorEntity.Credentials))
            return
        }

        val disposiable = Repository.autentificate(token, Login).subscribe(
            { next_item ->
                if (next_item.login == Login)_user.postValue(Result.Success(next_item))
                else _user.postValue(Result.Error(ErrorEntity.Credentials))
            }
        ) { Error ->
            Error.message; if (Error.message.equals("HTTP 401")) _user.postValue(
                Result.Error(
                    ErrorEntity.Credentials
                )
            )
            else if (Error.message.equals("Unable to resolve host ")) _user.postValue(
                Result.Error(
                    ErrorEntity.Network
                )
            )
        }
        disposiables.add(disposiable)
    }

    override fun onCleared() {
        super.onCleared()
        disposiables.clear()
    }
}
