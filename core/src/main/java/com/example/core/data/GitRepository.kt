package com.example.core.data


interface GitRepository {
    fun getRepository(UserName: String)
    fun autentificate(token: String, login: String)
}

/*
Paging library is split into multiple artifacts. There is paging-common,
which is a pure kotlin library that contains PagingSource and RemoteMediator
among others. Then, there is a paging-runtime which is an android library
 */
