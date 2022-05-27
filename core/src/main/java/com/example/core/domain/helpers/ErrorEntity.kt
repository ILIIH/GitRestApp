package com.example.core.domain.helpers

sealed class ErrorEntity {
    object Network : ErrorEntity()

    object Credentials : ErrorEntity()

    object MissTocken : ErrorEntity()
}
