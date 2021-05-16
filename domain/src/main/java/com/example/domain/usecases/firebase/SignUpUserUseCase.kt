package com.example.domain.usecases.firebase

import com.example.domain.respositories.firebase.AuthenticationRepository
import javax.inject.Inject

class SignUpUserUseCase @Inject constructor(private val firebaseAuthRepository: AuthenticationRepository){

    suspend fun signUpUser(email: String, password: String) = firebaseAuthRepository.signUpUser(email, password)
}