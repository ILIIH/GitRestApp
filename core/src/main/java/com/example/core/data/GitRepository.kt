package com.example.core.data

interface GitRepository {
    fun getRepository(UserName: String)
    fun autentificate(token: String, login: String)
}


