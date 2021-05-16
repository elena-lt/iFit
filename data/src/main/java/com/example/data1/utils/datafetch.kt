package com.example.data1.utils

//    fun getAllRuns2() = networkBoundResource(
//        query = {
//            runDao.getAllRuns()
//        },
//        fetch = {
//            firestore.collection("users").document(email).collection("runs")
//        },
//        saveFetchResult = {
//            db.withTransaction {
//                runDao.deleteAll()
//                runDao.insertRunIntoDB(it)
//            }
//        }
//    )

//    fun logout() = mAuth.signOut()