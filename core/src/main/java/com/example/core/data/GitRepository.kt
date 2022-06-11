package com.example.core.data

import androidx.paging.PagingData
import com.example.core.domain.Repo
import com.example.core.domain.User
import io.reactivex.Flowable
import io.reactivex.Single

interface GitRepository {
    fun getRepository(UserName: String): Flowable<PagingData<Repo>>
    fun authenticate(token: String): Single<User>
}
