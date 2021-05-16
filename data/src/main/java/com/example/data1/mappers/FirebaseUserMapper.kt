package com.example.data1.mappers

import com.example.data1.network.models.firebase.UserFirebaseResponse
import com.example.domain.models.firebase.User
import com.google.firebase.auth.FirebaseUser

class FirebaseUserMapper {

    fun toUser(firebaseUser: FirebaseUser): User {
        return User(
            firebaseUser.email!!,
            firebaseUser.uid,
            firebaseUser.photoUrl?.toString()

        )
    }
}