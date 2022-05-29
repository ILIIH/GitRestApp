package com.example.core.data

import com.example.core.domain.User
import io.reactivex.Observable

interface GitRepository {
    fun getRepository(UserName: String): Any
    fun autentificate(token: String, login: String): Observable<User>
}
