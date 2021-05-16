package com.example.ifit.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecases.firebase.LogoutUserUsecase
import com.example.domain.utils.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(private val logoutUserUsecase: LogoutUserUsecase) : ViewModel() {

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