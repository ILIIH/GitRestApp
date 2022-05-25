package com.example.gitapp.presentation.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.core.Domain.Repo
import com.example.core.Domain.User
import com.example.core.Domain.helpers.Result
import com.example.gitapp.framework.GithubRepository
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class ProfileViewModel @Inject constructor(private val Repository: GithubRepository) : ViewModel() {

    var disposiables: CompositeDisposable = Repository.disposables
    private var _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    private var _repo = Repository.repo
    val repo: LiveData<Result<List<Repo>>>
        get() = _repo

    fun setsUser(current_user: User) {
        _user.postValue(current_user)
        Repository.getRepository(current_user.login)
    }

    override fun onCleared() {
        super.onCleared()
        disposiables.clear()
    }
}
