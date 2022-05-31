package com.example.profile.profile.profileFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.example.core.domain.Repo
import com.example.core.domain.User
import com.example.gitapp.framework.GithubRepository
import javax.inject.Inject

class ProfileViewModel @Inject constructor(private val Repository: GithubRepository) : ViewModel() {
    
    private var _user = MutableLiveData<User>()

    val repo: LiveData<PagingData<Repo>> = Repository.repo

    fun setsUser(current_user: User) {
        _user.postValue(current_user)
        Repository.getRepository(current_user.login)
    }

    override fun onCleared() {
        super.onCleared()
        Repository.disposiables.clear()
    }
}
