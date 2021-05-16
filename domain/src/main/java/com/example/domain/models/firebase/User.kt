package com.example.domain.models.firebase

data class User(

    val email: String,
    val uid: String,
    val photoUrl: String? = null,
)
