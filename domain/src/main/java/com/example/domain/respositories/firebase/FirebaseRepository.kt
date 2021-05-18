package com.example.domain.respositories.firebase

import com.example.domain.models.firebase.Run
import com.example.domain.models.firebase.RunHistory
import com.example.domain.models.firebase.User
import com.example.domain.utils.AuthResult
import com.example.domain.utils.Resource

interface FirebaseRepository {

    suspend fun signInUser(email: String, password: String): AuthResult<User>

    suspend fun signUpUser(email: String, password: String): AuthResult<User>

    suspend fun saveUserData(
        firstName: String,
        lastName: String,
        weight: Double
    )

    suspend fun logoutUser(): AuthResult<User>

    suspend fun saveRun(run: Run): Resource<Run>

    suspend fun saveRunLocally(run: Run)

    suspend fun loadRunHistory(email: String): Resource<RunHistory>

}