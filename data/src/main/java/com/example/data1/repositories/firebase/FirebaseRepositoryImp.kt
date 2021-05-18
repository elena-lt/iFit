package com.example.data1.repositories.firebase

import com.example.data1.database.entities.Run
import com.example.domain.models.firebase.RunHistory
import com.example.domain.models.firebase.User
import com.example.domain.respositories.firebase.FirebaseRepository
import com.example.domain.utils.AuthResult
import com.example.domain.utils.Resource
import com.google.firebase.storage.StorageReference
import javax.inject.Inject

class FirebaseRepositoryImp @Inject constructor(
    private val firebaseSource: FirebaseSource,
    private val roomDataSource: RoomDataSource
) : FirebaseRepository {

    override suspend fun signInUser(email: String, password: String) = firebaseSource.signInUser(email, password)

    override suspend fun signUpUser(email: String, password: String): AuthResult<User> =
        firebaseSource.signUpUser(email, password)

    override suspend fun saveUserData(
        firstName: String,
        lastName: String,
        weight: Double
    )= firebaseSource.saveUserData(firstName, lastName, weight)

    override suspend fun logoutUser(): AuthResult<User> = firebaseSource.logout()

    override suspend fun saveRun(run: com.example.domain.models.firebase.Run): Resource<com.example.domain.models.firebase.Run> = firebaseSource.saveRun(run)


    override suspend fun saveRunLocally(run: com.example.domain.models.firebase.Run) {
        val runToSave = Run(run.timeStamp, run.image, run.avgSpeed, run.caloriesBurned, run.distanceInKilometers, run.timeInMillis)
        roomDataSource.saveRun(runToSave)
    }

    override suspend fun loadRunHistory(email: String): Resource<RunHistory> = firebaseSource.loadRunHistory(email)
}