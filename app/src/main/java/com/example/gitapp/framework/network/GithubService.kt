package com.example.gitapp.framework.network
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubService {
    @GET("/user")
    fun autintificate(@Header("Authorization") accessToken: String): Observable<UserNetworkEntity>

    @GET("/users/{username}/repos")
    fun getRepo(@Path("username",) username: String?): Observable<List<RepoNetworkEntity>>

    @GET("/users/{username}/repos")
    fun getRepoPerPage(@Path("username",) username: String?, @Query("page") page: Int, @Query("per_page") per_page: Int): Single<List<RepoNetworkEntity>>
}
