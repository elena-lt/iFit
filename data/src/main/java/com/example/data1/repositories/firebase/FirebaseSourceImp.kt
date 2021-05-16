package com.example.data1.repositories.firebase

import android.util.Log
import com.example.data1.mappers.FirebaseUserMapper
import com.example.data1.utils.await
import com.example.domain.models.firebase.User
import com.example.domain.utils.AuthResult
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import java.util.*

class FirebaseSourceImp(
    private val firebaseAuth: FirebaseAuth,
    private var fireStore: FirebaseFirestore,
    private val mapper: FirebaseUserMapper
) : FirebaseSource {

    companion object {
        const val TAG = "FirebaseAuthSourceImp"
    }

    @ExperimentalCoroutinesApi
    override suspend fun signInUser(email: String, password: String): AuthResult<User> = withContext(Dispatchers.IO) {
        try {

            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()

            if (result.user != null) {
                return@withContext AuthResult.AUTHENTICATED(mapper.toUser(result.user!!))
            } else {
                return@withContext AuthResult.UNAUTHENTICATED(null, "Unknown error")
            }
        } catch (e: FirebaseException) {
            return@withContext AuthResult.UNAUTHENTICATED(null, e.localizedMessage)
        }
    }

    @ExperimentalCoroutinesApi
    override suspend fun signUpUser(email: String, password: String): AuthResult<User> = withContext(Dispatchers.IO) {
        try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            if (result.user != null) {
                return@withContext AuthResult.AUTHENTICATED(mapper.toUser(result.user!!))
            } else {
                return@withContext AuthResult.UNAUTHENTICATED(null, "Unknown error")
            }
        } catch (e: FirebaseException) {
            return@withContext AuthResult.UNAUTHENTICATED(null, e.localizedMessage)
        }
    }

    @ExperimentalCoroutinesApi
    override suspend fun saveUserData(
        firstName: String,
        lastName: String,
        weight: Double
    ): Unit = withContext(Dispatchers.IO) {
        try{
            val timeStamp = Calendar.getInstance().time
            val email = firebaseAuth.currentUser?.email
            val uid = firebaseAuth.currentUser?.uid
            val user = com.example.data1.network.models.firebase.User(email, firstName, lastName, timeStamp, uid, weight)
            fireStore.collection("Users").document(email!!).set(user)

        }catch (e: FirebaseException){
            Log.d (TAG, e.message!!)
        }
    }

    override suspend fun logout(): AuthResult<User> = withContext(Dispatchers.IO) {
        try {
            val result = firebaseAuth.signOut()
            return@withContext AuthResult.LOGGEDOUT()
        } catch (e: FirebaseException) {
            Log.d(TAG, e.localizedMessage!!)
            return@withContext AuthResult.AUTHENTICATED(mapper.toUser(firebaseAuth.currentUser!!))
        }
    }

}