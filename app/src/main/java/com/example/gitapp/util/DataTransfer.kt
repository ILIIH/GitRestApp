package com.example.gitapp.util

import com.example.core.domain.Repo
import com.example.core.domain.User
import com.example.gitapp.framework.network.RepoNetworkEntity
import com.example.gitapp.framework.network.UserNetworkEntity

fun RepoNetworkEntity.asRepoDomain(): Repo {
    return Repo(id, name, fullName, description, url, stars, forks, language)
}
fun UserNetworkEntity.asUserDomain(): User {
    return User(avatar_url, disk_usage, id, login, public_repos)
}
fun User.asUserNetwork(): UserNetworkEntity {
    return UserNetworkEntity(avatar_url, disk_usage, id, login, public_repos)
}
fun Repo.asRepoNetworkEntity(): RepoNetworkEntity {
    return RepoNetworkEntity(id, name, fullName, description, url, stars, forks, language)
}
