package com.example.ifit.mappers

import com.example.domain.models.firebase.RunHistory
import com.example.ifit.models.FirebaseRun
import com.example.ifit.models.Run

class RunMapper {

    fun toRun(list: RunHistory): MutableList<FirebaseRun> {
        val runs = list.runs.map {
            FirebaseRun(it.imageUrl, it.timeStamp, it.avgSpeed, it.caloriesBurned, it.distanceInKilometers, it.timeInMillis)
        }

        return runs.toMutableList()
    }
}