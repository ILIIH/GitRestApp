package com.example.gitapp.framework

import com.example.core.domain.Repo
import com.example.core.domain.User
import com.example.core.data.GitRepository
import com.example.gitapp.framework.network.GithubService
import com.example.gitapp.util.asRepoDomain
import com.example.gitapp.util.asUserDomain
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GithubRepository @Inject constructor(val gitServise: GithubService) : GitRepository {

    override fun autentificate(token: String, login: String): Observable<User> {

        return gitServise.autintificate("token $token")
            .subscribeOn(Schedulers.io())
            .map { it.asUserDomain() }
    }

    override fun getRepository(UserName: String): Observable<List<Repo>> {
        return gitServise.getRepo(UserName).subscribeOn(Schedulers.io())
            .map { it.map { it -> it.asRepoDomain() } }
    }
}
