package com.example.core.domain.helpers

sealed class Result<T> {

    data class Success<T >(val data: T) : Result<T>()

    data class Error<T >(val error: ErrorEntity) : Result<T>()
}

