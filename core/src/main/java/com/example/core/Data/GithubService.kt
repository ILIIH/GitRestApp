package com.example.core.Data

import com.example.core.Domain.GitHubProfile
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface GithubService {


    @GET("/user")
    suspend fun searchRepos(@Header("Accept")  credentials :String): GitHubProfile

}