package com.example.data1.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.data1.database.converters.ImageConverter
import com.example.data1.database.converters.TimeConverter
import com.example.data1.database.entities.Run

@Database(
    entities = [Run::class],
    version = 1
)
@TypeConverters (ImageConverter::class, TimeConverter::class)
abstract class RunDatabase : RoomDatabase() {

    abstract fun runDao(): RunDao
}