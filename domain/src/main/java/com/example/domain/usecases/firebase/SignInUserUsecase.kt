package com.example.domain.usecases.firebase

import com.example.domain.respositories.firebase.AuthenticationRepository
import javax.inject.Inject

class SignInUserUsecase @Inject constructor (private val firebaseAuthRepository: AuthenticationRepository){

    suspend fun signInUser(email: String, password: String) = firebaseAuthRepository.signInUser(email, password)
}