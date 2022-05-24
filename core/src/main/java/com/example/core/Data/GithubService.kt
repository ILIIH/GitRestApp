package com.example.core.Data

import com.example.core.Domain.Repo
import com.example.core.Domain.User
import io.reactivex.Observable
import retrofit2.http.*


interface GithubService {

    @GET("/user")
    fun Autintificate(@Header("Authorization") accessToken: String): Observable<User>

    @GET("/users/{username}/repos")
    fun getRepo(@Path("username",) username: String?): Observable<List<Repo>>

}