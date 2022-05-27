package com.example.gitapp.framework.network
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface GithubService {
    @GET("/user")
    fun autintificate(@Header("Authorization") accessToken: String): Observable<UserNetworkEntity>

    @GET("/users/{username}/repos")
    fun getRepo(@Path("username",) username: String?): Observable<List<RepoNetworkEntity>>
}
