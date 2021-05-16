package com.example.data1.network.models.firebase

import com.google.firebase.firestore.Exclude
import java.util.*

data class UserFirebaseResponse(

    val email: String,
    val uid: String
)

data class User (
    val email: String? = null,
    val userFirstName: String? = null,
    val userLastName: String? = null,
    val memberSince: Date? = null,
    val userUUID: String? = null,
    val weight: Double? = null,

)
