package com.example.domain.usecases.firebase

import com.example.domain.respositories.firebase.FirebaseRepository
import javax.inject.Inject

class SignInUserUsecase @Inject constructor (private val firebaseAuthRepository: FirebaseRepository){

    suspend fun signInUser(email: String, password: String) = firebaseAuthRepository.signInUser(email, password)
}