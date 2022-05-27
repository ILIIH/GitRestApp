package com.example.profile.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.core.domain.Repo
import com.example.core.domain.User
import com.example.core.domain.helpers.ErrorEntity
import com.example.core.domain.helpers.Result
import com.example.gitapp.framework.GithubRepository
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class ProfileViewModel @Inject constructor(private val Repository: GithubRepository) : ViewModel() {

    var disposiables: CompositeDisposable = CompositeDisposable()
    private var _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    private var _repo = MutableLiveData<Result<List<Repo>>>()
    val repo: LiveData<Result<List<Repo>>>
        get() = _repo

    fun setsUser(current_user: User) {
        _user.postValue(current_user)

        val disposiable = Repository.getRepository(current_user.login).subscribe(
            { next_item -> _repo.postValue(Result.Success(next_item)) },
            { _repo.postValue(Result.Error(ErrorEntity.Network)) }
        )

        disposiables.add(disposiable)
    }

    override fun onCleared() {
        super.onCleared()
        disposiables.clear()
    }
}
