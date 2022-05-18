package com.example.core.Data

import com.example.core.Domain.GitHubProfile
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface GithubService {


    @GET("/user")
    fun searchRepos(@Header("Accept")  credentials :String): Observable<GitHubProfile>

}