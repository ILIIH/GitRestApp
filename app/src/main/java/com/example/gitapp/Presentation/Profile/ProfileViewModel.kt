package com.example.gitapp.Presentation.Profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.core.Data.GithubRepository
import com.example.core.Domain.Repo
import com.example.core.Domain.User
import com.example.core.Domain.helpers.ErrorEntity
import com.example.core.Domain.helpers.Result

class ProfileViewModel (private val Repository: GithubRepository) : ViewModel() {

    private var _user = MutableLiveData<User>();
    val user : LiveData<User>
        get() = _user

    private var _repo = MutableLiveData<Result<List<Repo>>>();
    val repo : LiveData<Result<List<Repo>>>
        get() = _repo


    fun setsUser(current_user : User){
        _user.postValue(current_user)

        Repository.GetRepository(current_user.login).
        subscribe(
                { next_item ->_repo.postValue(Result.Success(next_item)) },
                {error -> _repo.postValue(Result.Error(ErrorEntity.Network))}
        )
    }

}