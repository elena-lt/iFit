package com.example.ifit.ui.authentication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecases.firebase.LogoutUserUsecase
import com.example.domain.usecases.firebase.SaveUserDataUsecase
import com.example.domain.usecases.firebase.SignInUserUsecase
import com.example.domain.usecases.firebase.SignUpUserUseCase
import com.example.domain.utils.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val signInUserUsecase: SignInUserUsecase,
    private val signUpUserUsecase: SignUpUserUseCase,
    private val saveUserDataUsecase: SaveUserDataUsecase,
    private val logoutUserUsecase: LogoutUserUsecase
) : ViewModel() {

    private val _dataLoading = MutableLiveData(false)
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _isUserAuthenticated = MutableLiveData(false)
    val isUserAuthenticated: LiveData<Boolean> = _isUserAuthenticated

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun signInUser(email: String, password: String) {
        _dataLoading.postValue(true)

        viewModelScope.launch {
            when (val result = signInUserUsecase.signInUser(email, password)) {
                is AuthResult.AUTHENTICATED -> {
                    _dataLoading.postValue(false)
                    _isUserAuthenticated.postValue(true)
                }
                is AuthResult.UNAUTHENTICATED -> {
                    //_isUserAuthenticated.postValue(false)
                    _dataLoading.postValue(false)
                    _error.postValue(result.message!!)
                }
            }
        }
    }

    fun signUpUser(email: String, password: String, repeatPassword: String) {
        //TODO(validation)

        viewModelScope.launch {
            _dataLoading.postValue(true)
            when (val result = signUpUserUsecase.signUpUser(email, password)) {

                is AuthResult.AUTHENTICATED -> {
                    _dataLoading.postValue(false)
                    _isUserAuthenticated.postValue(true)
                }
                is AuthResult.UNAUTHENTICATED -> {
                    _dataLoading.postValue(false)
                    _error.postValue(result.message!!)
                }
            }
        }
    }

    fun saveUserData (firstName: String, lastName: String, dateOfBirth: String, weight: Double){
        viewModelScope.launch {
            saveUserDataUsecase.saveUserData(firstName, lastName, )
        }
    }

}