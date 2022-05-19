package com.example.gitapp.Presentation.Login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.core.Data.GithubRepository
import com.example.core.Domain.User
import javax.inject.Inject


class LoginViewModel constructor(private val Repository: GithubRepository) :ViewModel() {

    private var _user = MutableLiveData<User>();
    val user : LiveData<User>
        get() = _user
}