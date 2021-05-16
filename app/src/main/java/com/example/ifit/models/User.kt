package com.example.ifit.models

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.Exclude
import java.util.*

data class User(

    val email: String,
    val userFirstName: String,
    val userLastName: String,
    val memberSince: Date,
    val userUUID: String,
    val weight: Double? = null,
    @Exclude
    var isNew: Boolean? = true

)
