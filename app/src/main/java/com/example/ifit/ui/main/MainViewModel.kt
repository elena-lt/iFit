package com.example.ifit.ui.main

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecases.firebase.LoadRunHistoryUsecase
import com.example.domain.usecases.firebase.LogoutUserUsecase
import com.example.domain.usecases.firebase.SaveRunUsecase
import com.example.domain.utils.AuthResult
import com.example.domain.utils.Resource
import com.example.ifit.mappers.RunMapper
import com.example.ifit.models.FirebaseRun
import com.example.ifit.models.Run
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val saveRunUsecase: SaveRunUsecase,
    private val loadRuns: LoadRunHistoryUsecase,
    private val logoutUserUsecase: LogoutUserUsecase,
    private val runMapper: RunMapper
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

    private val _runs = MutableLiveData<MutableList<FirebaseRun>>()
    val runs: LiveData<MutableList<FirebaseRun>> = _runs

    fun saveRun(bitmap: Bitmap, timeStamp: Long, avgSpeed: Double, caloriesBurned: Int, distance: Double, timeRun: Long) {
        viewModelScope.launch {
            val result = saveRunUsecase.saveRun(bitmap, timeStamp, avgSpeed, caloriesBurned, distance, timeRun)
        }
    }

    fun loadRuns (email: String){
        viewModelScope.launch {
            when (val result = loadRuns.loadRunHistory(email) ){
                is Resource.SUCCESS -> {
                    _runs.postValue(runMapper.toRun(result.data!!))
                }
                is Resource.ERROR -> {
                    _error.postValue(result.message!!)
                }
            }
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