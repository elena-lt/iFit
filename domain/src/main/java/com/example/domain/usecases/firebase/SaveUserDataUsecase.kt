package com.example.domain.usecases.firebase

import com.example.domain.respositories.firebase.FirebaseRepository
import javax.inject.Inject

class SaveUserDataUsecase @Inject constructor(private val firebaseAuthRepository: FirebaseRepository) {

    suspend fun saveUserData(firstName: String, lastName: String, weight: Double) = firebaseAuthRepository.saveUserData(firstName, lastName, weight)
}