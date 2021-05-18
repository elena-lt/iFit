package com.example.data1.repositories.firebase

import androidx.room.RoomDatabase
import com.example.data1.database.RunDao
import com.example.data1.database.entities.Run

class RoomDataSourceImp (
    private val database: RunDao
        ) : RoomDataSource {

    override suspend fun saveRun(run: Run) = database.insertRun(run)

}