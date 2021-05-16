package com.example.data1.repositories.firebase

import com.example.domain.models.firebase.User
import com.example.domain.respositories.firebase.AuthenticationRepository
import com.example.domain.utils.AuthResult
import java.util.*
import javax.inject.Inject

class FirebaseRepositoryImp @Inject constructor(
    private val firebaseAuth: FirebaseSource
) : AuthenticationRepository {
    override suspend fun signInUser(email: String, password: String) = firebaseAuth.signInUser(email, password)

    override suspend fun signUpUser(email: String, password: String): AuthResult<User> =
        firebaseAuth.signUpUser(email, password)

    override suspend fun saveUserData(
        firstName: String,
        lastName: String,
        weight: Double
    )= firebaseAuth.saveUserData(firstName, lastName, weight)

    override suspend fun logoutUser(): AuthResult<User> = firebaseAuth.logout()
}