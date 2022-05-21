package com.example.gitapp.Presentation.Login

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.core.Data.GithubRepository
import com.example.core.Domain.User
import com.example.core.Domain.helpers.ErrorEntity
import com.example.core.Domain.helpers.Result
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import kotlin.coroutines.coroutineContext


class LoginViewModel (private val Repository: GithubRepository) :ViewModel() {

    private var _user = MutableLiveData<Result<User>>();
    val user : LiveData<Result<User>>
        get() = _user

    fun autintificate(token:String, Login : String){

        if(Login.length<4||token.length<8) {
            _user.postValue(Result.Error(ErrorEntity.Credentials))
            return
        }

        Repository.Autentificate(token).
        subscribe(
        { next_item ->
            if(next_item.login.equals(Login))_user.postValue(Result.Success(next_item))
            else _user.postValue(Result.Error(ErrorEntity.Credentials))
             },
        { Error ->
            var measage = Error.message ;
            if(Error.message.equals("HTTP 401"))_user.postValue(Result.Error(ErrorEntity.Credentials))
            else if(Error.message.equals("Unable to resolve host "))_user.postValue(Result.Error(ErrorEntity.Network))
            }
        )

    }
}