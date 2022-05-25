package com.example.core.Domain.helpers

sealed class ErrorEntity {
    object Network : ErrorEntity()

    object Credentials : ErrorEntity()

    object MissTocken : ErrorEntity()
}
