package com.example.core.Domain.helpers

sealed class Result<T> {

    data class Success<T>(val data: T) : Result<T>()

    data class Error<T>(val error: ErrorEntity) : Result<T>()
}
