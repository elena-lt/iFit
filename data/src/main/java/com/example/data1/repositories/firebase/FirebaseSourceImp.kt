package com.example.data1.repositories.firebase

import android.graphics.Bitmap
import android.util.Log
import com.example.data1.mappers.FirebaseRunMapper
import com.example.data1.mappers.FirebaseUserMapper
import com.example.data1.utils.await
import com.example.domain.models.firebase.Run
import com.example.domain.models.firebase.Run2
import com.example.domain.models.firebase.RunHistory
import com.example.domain.models.firebase.User
import com.example.domain.utils.AuthResult
import com.example.domain.utils.Resource
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.*
import java.io.ByteArrayOutputStream
import java.util.*

class FirebaseSourceImp(
    private val firebaseAuth: FirebaseAuth,
    private var fireStore: FirebaseFirestore,
    private val mapper: FirebaseUserMapper,
    private val runMapper: FirebaseRunMapper
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
        try {
            val timeStamp = Calendar.getInstance().time
            val email = firebaseAuth.currentUser?.email
            val uid = firebaseAuth.currentUser?.uid
            val user =
                com.example.data1.network.models.firebase.User(email, firstName, lastName, timeStamp, uid, weight)
            fireStore.collection("Users").document(email!!).set(user)

        } catch (e: FirebaseException) {
            Log.d(TAG, e.message!!)
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

    @ExperimentalCoroutinesApi
    override suspend fun saveRun(run: Run): Resource<Run> = withContext(Dispatchers.IO) {
        var dataPosted = false
        var error = ""

        val url = saveImgToFirestore(run.image!!, run.timeStamp)

        Log.d(TAG, "Image URL: $url")

        val runToSave =
            Run2(url, run.timeStamp, run.avgSpeed, run.caloriesBurned, run.distanceInKilometers, run.timeInMillis)

        try {
            val data = fireStore.collection("Users").document(firebaseAuth.currentUser?.email!!).collection("Runs")
                .document("${run.timeStamp}").set(runToSave).addOnCompleteListener { task ->

                    if (task.isSuccessful) {
                        dataPosted = true
                    } else {
                        error = task.exception?.message.toString()
                        Log.d(TAG, "${task.exception?.message}")
                    }
                }.await()

            if (dataPosted) {
                return@withContext Resource.SUCCESS(null)
            } else {
                return@withContext Resource.ERROR(error)
            }
        } catch (e: FirebaseException) {
            return@withContext Resource.ERROR(e.localizedMessage)
        }
    }

    @ExperimentalCoroutinesApi
    suspend fun saveImgToFirestore(run: Bitmap, timeStamp: Long): String {

        Log.d (TAG, "Inside Save img to Firebase fun")

        var url = ""

        val storage = Firebase.storage.reference
        val runImageRef = storage.child("runImages/$timeStamp.jpg")

        val baos = ByteArrayOutputStream()
        run.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val img = baos.toByteArray()

        val result = runImageRef.putBytes(img).addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d(TAG, "Success")
            } else {
                Log.d(TAG, "Error: ${it.exception?.message}")
            }
        }.await()

        return getImageUrl(runImageRef)
    }

    @ExperimentalCoroutinesApi
    private suspend fun getImageUrl(runImageRef: StorageReference): String {
        var uri = ""
        runImageRef.downloadUrl.addOnSuccessListener {
            uri = it.toString()
        }.await()

        return uri
    }


    override suspend fun loadRunHistory(email: String): Resource<RunHistory> =
        withContext(Dispatchers.IO) {
            try {
                val result = fireStore.collection("Users").document("$email").collection("Runs").get().await()
                Log.d(TAG, "Result successful")
                return@withContext Resource.SUCCESS(runMapper.toRun(result))
            } catch (e: FirebaseException) {
                Log.d(TAG, "ERROR: ${e.message}")
                return@withContext Resource.ERROR(e.localizedMessage, null)
            }
        }

}