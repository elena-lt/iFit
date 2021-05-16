package com.example.data1.repositories.firebase

import com.example.domain.models.firebase.Run
import com.example.domain.models.firebase.User
import com.example.domain.respositories.firebase.FirebaseRepository
import com.example.domain.utils.AuthResult
import com.example.domain.utils.Resource
import javax.inject.Inject

class FirebaseRepositoryImp @Inject constructor(
    private val firebaseSource: FirebaseSource
) : FirebaseRepository {

    override suspend fun signInUser(email: String, password: String) = firebaseSource.signInUser(email, password)

    override suspend fun signUpUser(email: String, password: String): AuthResult<User> =
        firebaseSource.signUpUser(email, password)

    override suspend fun saveUserData(
        firstName: String,
        lastName: String,
        weight: Double
    )= firebaseSource.saveUserData(firstName, lastName, weight)

    override suspend fun logoutUser(): AuthResult<User> = firebaseSource.logout()

    override suspend fun saveRun(run: Run): Resource<Run> = firebaseSource.saveRun(run)
}