package com.example.core.domain

data class Repo(
    val id: Long,
    var name: String,
    val fullName: String,
    val description: String?,
    val url: String,
    val stars: Int,
    val forks: Int,
    val language: String?
)
