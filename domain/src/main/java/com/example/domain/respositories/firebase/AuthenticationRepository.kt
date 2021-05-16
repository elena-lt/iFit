package com.example.domain.respositories.firebase

import com.example.domain.models.firebase.User
import com.example.domain.utils.AuthResult
import java.util.*


interface AuthenticationRepository {

    suspend fun signInUser(email: String, password: String): AuthResult<User>

    suspend fun signUpUser(email: String, password: String): AuthResult<User>

    suspend fun saveUserData(
        firstName: String,
        lastName: String,
        weight: Double
    )

    suspend fun logoutUser(): AuthResult<User>
}