package com.example.domain.usecases.firebase

import android.graphics.Bitmap
import com.example.domain.models.firebase.Run
import com.example.domain.respositories.firebase.FirebaseRepository
import javax.inject.Inject

class SaveRunUsecase @Inject constructor (private val repository: FirebaseRepository) {


    suspend fun saveRun(
        bitmap: Bitmap,
        timeStamp: Long,
        avgSpeed: Double,
        caloriesBurned: Int,
        distance: Double,
        timeRun: Long
    ) {

        val run = Run(bitmap, timeStamp, avgSpeed, caloriesBurned, distance, timeRun)
        repository.saveRun(run)
        repository.saveRunLocally(run)
    }
}