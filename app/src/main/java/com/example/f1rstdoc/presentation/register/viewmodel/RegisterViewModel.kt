package com.example.f1rstdoc.presentation.register.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.f1rstdoc.domain.firebase.usecase.FirebaseAuthUseCase
import com.example.f1rstdoc.domain.firebase.model.FirebaseAuthResult
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val firebaseAuthUseCase: FirebaseAuthUseCase
) : ViewModel(){

    private val _stateCreateUser by lazy { MutableLiveData<FirebaseAuthResult<Boolean>>() }
    val stateCreateUser: LiveData<FirebaseAuthResult<Boolean>> get()= _stateCreateUser

    fun registerUser(email: String, senha : String) {
        viewModelScope.launch {
            firebaseAuthUseCase.createUser(email, senha) { _stateCreateUser.value = it }
        }
    }
}