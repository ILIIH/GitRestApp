package com.example.gitapp.Presentation.Profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.core.Data.GithubRepository
import com.example.core.Domain.User
import com.example.core.Domain.helpers.Result

class ProfileViewModel (private val Repository: GithubRepository) : ViewModel() {

    private var _user = MutableLiveData<User>();
    val user : LiveData<User>
        get() = _user


    fun setsUser(current_user : User){
        _user.postValue(current_user)
    }

}