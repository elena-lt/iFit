package com.example.data1.repositories.firebase

import com.example.domain.models.firebase.User
import com.example.domain.utils.AuthResult
import java.util.*

interface FirebaseAuthSource {

    suspend fun signInUser (email: String, password: String): AuthResult<User>

    suspend fun signUpUser (email: String, password: String): AuthResult<User>

    suspend fun saveUserData(email: String, firstName: String, lastName: String, memberSince: Date, uid: String, weight: String )

    suspend fun logout (): AuthResult<User>
}