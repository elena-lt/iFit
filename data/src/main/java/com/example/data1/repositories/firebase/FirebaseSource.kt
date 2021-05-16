package com.example.data1.repositories.firebase

import com.example.domain.models.firebase.Run
import com.example.domain.models.firebase.User
import com.example.domain.utils.AuthResult
import com.example.domain.utils.Resource

interface FirebaseSource {

    suspend fun signInUser (email: String, password: String): AuthResult<User>

    suspend fun signUpUser (email: String, password: String): AuthResult<User>

    suspend fun saveUserData(firstName: String, lastName: String, weight: Double)

    suspend fun logout (): AuthResult<User>

    suspend fun saveRun (run: Run): Resource<Run>
}