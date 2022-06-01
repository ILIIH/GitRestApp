package com.example.gitapp.framework

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import com.example.core.data.GitRepository
import com.example.core.domain.Repo
import com.example.core.domain.User
import com.example.core.domain.helpers.ErrorEntity
import com.example.core.domain.helpers.Result
import com.example.gitapp.framework.network.GithubService
import com.example.gitapp.util.asUserDomain
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

open class GithubRepository @Inject constructor(
    val gitServise: GithubService,
) : GitRepository {

    var disposiables: CompositeDisposable = CompositeDisposable()
    private var _user = MutableLiveData<Result<User>>()
    val user: LiveData<Result<User>>
        get() = _user

    override fun autentificate(token: String, login: String) {

        if (login.length < 4 || token.length < 8) {
            _user.postValue(Result.Error(ErrorEntity.Credentials))
            return
        }

        val disposiable = gitServise.autintificate("token $token")
            .subscribeOn(Schedulers.io())
            .map { it.asUserDomain() }.subscribe(
                { next_item ->
                    if (next_item.login == login)_user.postValue(Result.Success(next_item))
                    else _user.postValue(Result.Error(ErrorEntity.Credentials))
                }
            ) { Error ->
                Error.message
                if (Error.message.equals("401"))
                    _user.postValue(
                        Result.Error(
                            ErrorEntity.Network
                        )
                    )
                else _user.postValue(
                    Result.Error(
                        ErrorEntity.Credentials
                    )
                )
            }

        disposiables.add(disposiable)
    }

    // ////////////////////////////////////////////////////////

    private var _repo = MutableLiveData<PagingData<Repo>>()
    val repo: LiveData<PagingData<Repo>>
        get() = _repo

    override fun getRepository(UserName: String) {

        val disposiable = Pager(
            config = PagingConfig(
                pageSize = 3,
                enablePlaceholders = false,
                prefetchDistance = 1
            ),
            pagingSourceFactory = { RepositoryPageSourse(gitServise, UserName) }
        ).flowable
            .subscribe {
                _repo.value = it
            }

        disposiables.add(disposiable)
    }
}
