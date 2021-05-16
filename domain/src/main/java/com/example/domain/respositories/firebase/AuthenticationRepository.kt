package com.example.domain.respositories.firebase

import com.example.domain.models.firebase.User
import com.example.domain.utils.AuthResult


interface AuthenticationRepository {

    suspend fun signInUser (email: String, password: String): AuthResult<User>

    suspend fun signUpUser (email: String, password: String): AuthResult<User>

    suspend fun saveUseData()

    suspend fun logoutUser (): AuthResult<User>
}