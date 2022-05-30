package com.example.core.data

import androidx.paging.PagingData
import com.example.core.domain.Repo
import com.example.core.domain.User
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single

interface GitRepository {
    fun getRepository(UserName: String): Flowable<PagingData<Repo>>
    fun autentificate(token: String, login: String): Single<User>
}
