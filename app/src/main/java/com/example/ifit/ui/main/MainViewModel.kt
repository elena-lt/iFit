package com.example.ifit.ui.main

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecases.firebase.LogoutUserUsecase
import com.example.domain.usecases.firebase.SaveRunUsecase
import com.example.domain.utils.AuthResult
import com.example.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val saveRunUsecase: SaveRunUsecase,
    private val logoutUserUsecase: LogoutUserUsecase
) : ViewModel() {

//    val allRuns = repository.getAllRuns().asLiveData()
////    val allRuns = repository.getAllRuns()
//
//    fun insertRunIntoDB(run: Run) = viewModelScope.launch {
//        repository.insertRunIntoDB(run)
//    }

    private val _dataLoading = MutableLiveData(false)
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _isUserAuthenticated = MutableLiveData(false)
    val isUserAuthenticated: LiveData<Boolean> = _isUserAuthenticated

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _runSaved = MutableLiveData(false)
    val runSaved: LiveData<Boolean> = _runSaved

    fun saveRun(bitmap: Bitmap, timeStamp: Long, avgSpeed: Double, caloriesBurned: Int, distance: Double, timeRun: Long) {
        viewModelScope.launch {
            val result = saveRunUsecase.saveRun(bitmap, timeStamp, avgSpeed, caloriesBurned, distance, timeRun)
        }
    }

    fun logoutUser() {
        viewModelScope.launch {
            when (val result = logoutUserUsecase.logoutUser()) {
                is AuthResult.LOGGEDOUT -> {
                    _isUserAuthenticated.postValue(false)
                }
                is AuthResult.AUTHENTICATED -> {
                    _isUserAuthenticated.postValue(true)
                    _error.postValue("Cannot log out, please retry")
                }
            }
        }
    }
}