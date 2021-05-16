package com.example.data1.repositories.firebase

import android.util.Log
import com.example.data1.mappers.FirebaseUserMapper
import com.example.data1.utils.await
import com.example.domain.models.firebase.User
import com.example.domain.utils.AuthResult
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.*

class FirebaseAuthSourceImp(
    private val firebaseAuth: FirebaseAuth,
    private val mapper: FirebaseUserMapper
) : FirebaseAuthSource {

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