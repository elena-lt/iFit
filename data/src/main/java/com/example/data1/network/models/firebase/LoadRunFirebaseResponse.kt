package com.example.data1.network.models.firebase

import android.graphics.Bitmap

data class LoadRunFirebaseResponse(
    val runList: List<Run>

)

data class Run(
    val image: Bitmap? = null,
    val timeStamp: Long = 0L,
    var avgSpeed: Double = 0.0,
    var caloriesBurned: Int = 0,
    var distanceInKilometers: Double = 0.0,
    var timeInMillis: Long = 0L,
)
