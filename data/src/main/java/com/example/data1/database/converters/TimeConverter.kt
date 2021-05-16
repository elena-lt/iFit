package com.example.data1.database.converters

import androidx.room.TypeConverter
import java.util.*

class TimeConverter {

    @TypeConverter
    fun fromTimeStamp (timeStamp: Long?): Date? {
        return timeStamp?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimeStamp (date: Date?): Long? {
        return date?.time
    }

}