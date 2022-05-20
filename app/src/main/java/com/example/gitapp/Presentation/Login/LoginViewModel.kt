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

    fun autintificate(token:String){

        Repository.Autentificate(token).
        subscribe(
        { next_item ->_user.postValue(Result.Success(next_item)) },
        {error -> _user.postValue(Result.Error(ErrorEntity.Network))}
        )

    }
}