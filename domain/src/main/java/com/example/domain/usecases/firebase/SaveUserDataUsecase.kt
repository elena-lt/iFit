package com.example.domain.usecases.firebase

import com.example.domain.respositories.firebase.AuthenticationRepository
import javax.inject.Inject

class SaveUserDataUsecase @Inject constructor(private val firebaseAuthRepository: AuthenticationRepository) {

    suspend fun saveUserData()
}