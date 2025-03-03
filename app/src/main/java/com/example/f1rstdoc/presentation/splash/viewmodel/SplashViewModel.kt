package com.example.f1rstdoc.presentation.splash.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.f1rstdoc.domain.sharedpreferences.usecase.PreferencesUserLoginUseCase
import com.example.f1rstdoc.presentation.splash.state.SessionState
import kotlinx.coroutines.launch

class SplashViewModel(
    private val preferencesUserLoginUseCase: PreferencesUserLoginUseCase,
    ): ViewModel() {

    private val _stateSessionState by lazy { MutableLiveData<SessionState>() }
    val stateSessionState: LiveData<SessionState> get() = _stateSessionState


    fun fetchUserSession(){
        viewModelScope.launch {
            _stateSessionState.postValue(SessionState.UserSession(preferencesUserLoginUseCase.getUserSessionStatus()))
        }
    }
}