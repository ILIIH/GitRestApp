package com.example.core.Domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Plan(
    val collaborators: Int,
    val name: String,
    val private_repos: Int,
    val space: Int
) : Parcelable