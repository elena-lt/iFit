package com.example.data1.database.entities

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "runs_table")
data class Run(

    @PrimaryKey
    val timestamp: Long,
    val image: Bitmap? = null,
    val avgSpeed: Double = 0.0,
    val caloriesBurned: Int = 0,
    val distanceInKilometers: Double = 0.0,
    val timeInMillis: Long = 0L

)
