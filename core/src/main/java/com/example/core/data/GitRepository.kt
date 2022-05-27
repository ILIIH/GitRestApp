package com.example.core.data

import com.example.core.domain.Repo
import com.example.core.domain.User
import io.reactivex.Observable

interface GitRepository {
    fun getRepository(UserName: String): Observable<List<Repo>>
    fun autentificate(token: String, login: String): Observable<User>
}
