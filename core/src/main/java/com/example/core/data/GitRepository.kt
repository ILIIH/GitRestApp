package com.example.core.data

import androidx.paging.PagingData
import com.example.core.domain.Repo
import io.reactivex.Flowable


interface GitRepository {
    fun getRepository(UserName: String): Flowable<PagingData<Repo>>
    fun autentificate(token: String, login: String)
}

/*
Paging library is split into multiple artifacts. There is paging-common,
which is a pure kotlin library that contains PagingSource and RemoteMediator
among others. Then, there is a paging-runtime which is an android library
 */