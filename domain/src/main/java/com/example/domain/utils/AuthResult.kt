package com.example.domain.utils

sealed class AuthResult<out T> {

    data class AUTHENTICATED<out T>(val data: T?) : AuthResult<T>()
    data class UNAUTHENTICATED<out T>(
        val data: T? = null,
        val message: String? = null
    ) : AuthResult<T>()

    class LOGGEDOUT<out T> : AuthResult<T>()
}