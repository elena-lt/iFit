package com.example.data1.mappers

import com.example.domain.models.firebase.Run
import com.example.domain.models.firebase.Run2
import com.example.domain.models.firebase.RunHistory
import com.google.firebase.firestore.QuerySnapshot

class FirebaseRunMapper {

    fun toRun (querySnapshot: QuerySnapshot): RunHistory {
        return RunHistory(querySnapshot.toObjects(Run2::class.java))
    }
}