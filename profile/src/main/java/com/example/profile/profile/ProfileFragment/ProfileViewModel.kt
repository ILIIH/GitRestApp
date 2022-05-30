package com.example.profile.profile.ProfileFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava2.cachedIn
import com.example.core.domain.Repo
import com.example.core.domain.User
import com.example.gitapp.framework.GithubRepository
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class ProfileViewModel @Inject constructor(private val Repository: GithubRepository) : ViewModel() {

    var disposiables: CompositeDisposable = CompositeDisposable()
    private var _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    private var _repo = MutableLiveData<PagingData<Repo>>()
    val repo: LiveData<PagingData<Repo>>
        get() = _repo

    fun setsUser(current_user: User) {
        _user.postValue(current_user)

        val disposiable = Repository.getRepository(current_user.login)
            .cachedIn(viewModelScope)
            .subscribe {
                var t = it
                _repo.value = it
            }

        disposiables.add(disposiable)
    }

    override fun onCleared() {
        super.onCleared()
        disposiables.clear()
    }
}
