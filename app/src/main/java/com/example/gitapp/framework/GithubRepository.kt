package com.example.gitapp.framework

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.core.Domain.Repo
import com.example.core.Domain.User
import com.example.core.Domain.helpers.ErrorEntity
import com.example.core.Domain.helpers.Result
import com.example.core.data.GitRepository
import com.example.gitapp.framework.network.GithubService
import com.example.gitapp.util.asRepoDomain
import com.example.gitapp.util.asUserDomain
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GithubRepository @Inject constructor(val gitServise: GithubService) : GitRepository {

    private var _user = MutableLiveData<Result<User>>()
    val user: LiveData<Result<User>>
        get() = _user

    private var _repo = MutableLiveData<Result<List<Repo>>>()
    val repo: LiveData<Result<List<Repo>>>
        get() = _repo

    override fun autentificate(token: String, login: String) {

        if (login.length < 4 || token.length < 8) {
            _user.postValue(Result.Error(ErrorEntity.Credentials))
            return
        }

        gitServise.autintificate("token $token").subscribeOn(Schedulers.io()).subscribeOn(Schedulers.io())
            .subscribe(
                { next_item ->
                    if (next_item.login == login)_user.postValue(Result.Success(next_item.asUserDomain()))
                    else _user.postValue(Result.Error(ErrorEntity.Credentials))
                }
            ) { Error ->
                Error.message; if (Error.message.equals("HTTP 401")) _user.postValue(
                    Result.Error(
                        ErrorEntity.Credentials
                    )
                )
                else if (Error.message.equals("Unable to resolve host ")) _user.postValue(
                    Result.Error(
                        ErrorEntity.Network
                    )
                )
            }
    }

    override fun getRepository(UserName: String) {
        gitServise.getRepo(UserName).subscribeOn(Schedulers.io()).subscribe(
            { next_item -> _repo.postValue(Result.Success(next_item.map { it.asRepoDomain() })) },
            { _repo.postValue(Result.Error(ErrorEntity.Network)) }
        )
    }
}
