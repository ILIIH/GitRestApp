package com.example.core.data

import com.example.core.Domain.Repo
import com.example.core.Domain.User
import io.reactivex.ObservableSource

interface GitService {
    fun autintificate(accessToken: String): ObservableSource<User> fun getRepo(username: String?): ObservableSource<List<Repo>>
}
