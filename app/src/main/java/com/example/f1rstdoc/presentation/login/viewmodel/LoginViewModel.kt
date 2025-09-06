package com.example.f1rstdoc.presentation.login.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.f1rstdoc.domain.firebase.usecase.FirebaseAuthUseCase
import com.example.f1rstdoc.domain.firebase.model.FirebaseAuthResult
import com.example.f1rstdoc.domain.sharedpreferences.usecase.PreferencesUserLoginUseCase
import com.example.f1rstdoc.presentation.splash.state.SessionState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val firebaseAuthUseCase: FirebaseAuthUseCase,
    private val preferencesUserLoginUseCase: PreferencesUserLoginUseCase
) : ViewModel(){


    private val _stateLoginAuth = MutableStateFlow<FirebaseAuthResult<String>>(FirebaseAuthResult.Loading)
    val stateLoginAuth: StateFlow<FirebaseAuthResult<String>> = _stateLoginAuth

    fun loginAuth(email: String, senha : String) {
        viewModelScope.launch {
            firebaseAuthUseCase.singIn(email, senha) {
                _stateLoginAuth.value = it
            }
        }
    }

    fun saveUserPrefLogin(email: String, userUid: String){
        viewModelScope.launch {
            preferencesUserLoginUseCase.saveUserPref(true,email,userUid)
        }
    }
}