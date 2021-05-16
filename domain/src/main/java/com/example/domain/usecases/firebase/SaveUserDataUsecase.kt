package com.example.domain.usecases.firebase

import com.example.domain.respositories.firebase.AuthenticationRepository
import java.util.*
import javax.inject.Inject

class SaveUserDataUsecase @Inject constructor(private val firebaseAuthRepository: AuthenticationRepository) {

    suspend fun saveUserData(firstName: String, lastName: String, weight: Double) = firebaseAuthRepository.saveUserData(firstName, lastName, weight)
}