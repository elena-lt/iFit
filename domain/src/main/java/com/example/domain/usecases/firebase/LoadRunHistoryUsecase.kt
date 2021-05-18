package com.example.domain.usecases.firebase

import com.example.domain.respositories.firebase.FirebaseRepository
import javax.inject.Inject

class LoadRunHistoryUsecase  @Inject constructor(private val repository: FirebaseRepository){

    suspend fun loadRunHistory(email: String) = repository.loadRunHistory(email)
}