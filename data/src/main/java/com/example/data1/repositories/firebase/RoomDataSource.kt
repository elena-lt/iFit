package com.example.data1.repositories.firebase

import com.example.data1.database.entities.Run


interface RoomDataSource {

    suspend fun saveRun(run: Run)

}