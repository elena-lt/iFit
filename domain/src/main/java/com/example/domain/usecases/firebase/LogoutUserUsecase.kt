package com.example.domain.usecases.firebase

import com.example.domain.respositories.firebase.FirebaseRepository
import javax.inject.Inject

class LogoutUserUsecase @Inject constructor( private val firebaseAuthRepository: FirebaseRepository) {

    suspend fun logoutUser() = firebaseAuthRepository.logoutUser()
}