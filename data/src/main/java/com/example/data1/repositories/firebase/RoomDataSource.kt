package com.example.data1.repositories.firebase

import com.example.domain.models.firebase.Run
import com.example.domain.respositories.firebase.FirebaseRepository

interface RoomDataSource {

    suspend fun saveRun(run: Run)

}