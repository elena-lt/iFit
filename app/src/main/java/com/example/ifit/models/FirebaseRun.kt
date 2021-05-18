package com.example.ifit.models

import android.graphics.Bitmap

data class FirebaseRun(

    val imageUrl: String? = null,
    val timeStamp: Long = 0L,
    var avgSpeed: Double = 0.0,
    var caloriesBurned: Int = 0,
    var distanceInKilometers: Double = 0.0,
    var timeInMillis: Long = 0L,
)