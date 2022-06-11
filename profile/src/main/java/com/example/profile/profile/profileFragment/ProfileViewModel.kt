package com.example.profile.profile.profileFragment

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import com.example.core.domain.Repo
import com.example.core.domain.User
import com.example.gitapp.framework.GithubRepository
import com.example.gitapp.util.BaseViewModel
import javax.inject.Inject

class ProfileViewModel @Inject constructor(private val Repository: GithubRepository) :
    BaseViewModel() {
    
    var user = MutableLiveData<User>()

    private var _repo = MutableLiveData<PagingData<Repo>>()
    val repo: LiveData<PagingData<Repo>>
        get() = _repo

    @SuppressLint("CheckResult")
    fun setsUser(current_user: User) {
        user.postValue(current_user)
        val dispose = Repository.getRepository(current_user.login).subscribe {
            _repo.value = it
        }
        addDisposable { dispose }
    }

}
