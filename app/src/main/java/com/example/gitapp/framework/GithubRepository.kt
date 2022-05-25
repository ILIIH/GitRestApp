package com.example.gitapp.framework

import com.example.core.Domain.Repo
import com.example.core.Domain.User
import com.example.core.data.GitRepository
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GithubRepository @Inject constructor(val gitServise: GithubService) : GitRepository {

    override fun Autentificate(token: String): Observable<User> = gitServise.autintificate("token $token").subscribeOn(Schedulers.io())

    override fun GetRepository(UserName: String): Observable<List<Repo>> = gitServise.getRepo(UserName).subscribeOn(Schedulers.io())
}
