package com.example.core.Data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.core.Domain.User
import com.example.core.Domain.helpers.ErrorEntity
import com.example.core.Domain.helpers.Result
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GithubRepository @Inject constructor(val gitServise : GithubService ) {

    fun Autentificate (token : String ) = gitServise.Autintificate("token ${token}").subscribeOn(Schedulers.io());

    fun GetRepository ( UserName : String) =  gitServise.getRepo(UserName).subscribeOn(Schedulers.io());
}