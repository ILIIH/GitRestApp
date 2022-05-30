package com.example.gitapp.framework

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import com.example.core.data.GitRepository
import com.example.core.domain.Repo
import com.example.core.domain.User
import com.example.gitapp.framework.network.GithubService
import com.example.gitapp.util.asUserDomain
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

open class GithubRepository @Inject constructor(
    val gitServise: GithubService,
) :
    GitRepository {

    override fun autentificate(token: String, login: String): Single<User> {

        return gitServise.autintificate("token $token")
            .subscribeOn(Schedulers.io())
            .map { it.asUserDomain() }
    }

    override fun getRepository(UserName: String): Flowable<PagingData<Repo>> {
        return Pager(
            config = PagingConfig(
                pageSize = 3,
                enablePlaceholders = false,
                prefetchDistance = 1
            ),
            pagingSourceFactory = { RepositoryPageSourse(gitServise, UserName) }
        ).flowable
    }
}
