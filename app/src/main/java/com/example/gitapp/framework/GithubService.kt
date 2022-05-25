package com.example.gitapp.framework

import com.example.core.Domain.Repo
import com.example.core.Domain.User
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface GithubService {
    @GET("/user")
    fun autintificate(@Header("Authorization") accessToken: String): Observable<User>

    @GET("/users/{username}/repos")
    fun getRepo(@Path("username",) username: String?): Observable<List<Repo>>
}
