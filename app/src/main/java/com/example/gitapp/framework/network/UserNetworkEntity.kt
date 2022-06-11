package com.example.gitapp.framework.network

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserNetworkEntity(
    val avatar_url: String,
    val disk_usage: Int,
    val id: Int,
    val login: String,
    val public_repos: Int,

) : Parcelable
