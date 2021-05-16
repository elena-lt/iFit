package com.example.domain.usecases.firebase

import com.example.domain.respositories.firebase.FirebaseRepository
import javax.inject.Inject

class SignUpUserUseCase @Inject constructor(private val firebaseAuthRepository: FirebaseRepository){

    suspend fun signUpUser(email: String, password: String) = firebaseAuthRepository.signUpUser(email, password)
}