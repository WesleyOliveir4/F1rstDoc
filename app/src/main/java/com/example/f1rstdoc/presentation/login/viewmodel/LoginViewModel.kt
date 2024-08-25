package com.example.f1rstdoc.presentation.login.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.f1rstdoc.domain.firebase.usecase.FirebaseAuthUseCase
import com.example.f1rstdoc.domain.firebase.model.FirebaseAuthResult
import kotlinx.coroutines.launch

class LoginViewModel(
    private val firebaseAuthUseCase: FirebaseAuthUseCase
) : ViewModel(){

    private val _stateLoginAuth by lazy { MutableLiveData<FirebaseAuthResult<Boolean>>() }
    val stateLoginAuth: LiveData<FirebaseAuthResult<Boolean>> get()= _stateLoginAuth

    fun loginAuth(email: String, senha : String) {
        viewModelScope.launch {
            firebaseAuthUseCase.singIn(email, senha) { _stateLoginAuth.value = it }
        }
    }
}