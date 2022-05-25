package com.example.core.data

import com.example.core.Domain.Repo
import com.example.core.Domain.User
import io.reactivex.Observable

interface GitRepository {
    fun Autentificate(token: String): Observable<User>?
    fun GetRepository(UserName: String): Observable<List<Repo>>?
}
