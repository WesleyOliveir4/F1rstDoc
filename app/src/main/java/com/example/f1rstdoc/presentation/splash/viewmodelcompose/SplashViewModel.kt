package com.example.f1rstdoc.presentation.splash.viewmodelcompose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.f1rstdoc.domain.sharedpreferences.usecase.PreferencesUserLoginUseCase
import com.example.f1rstdoc.presentation.splash.state.SessionState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SplashViewModel(
    private val preferencesUserLoginUseCase: PreferencesUserLoginUseCase,
) : ViewModel() {

    // Usar MutableStateFlow para estado interno
    private val _sessionState = MutableStateFlow<SessionState>(SessionState.Loading) // Estado inicial
    // Expor como StateFlow (imutável) para a UI
    val sessionState: StateFlow<SessionState> = _sessionState.asStateFlow()

    fun fetchUserSession() {
        viewModelScope.launch {
            // Se SessionState.Loading não foi emitido antes, pode emitir aqui
            // _sessionState.value = SessionState.Loading // Opcional, dependendo se você quer mostrar carregamento antes da chamada
            try {
                val userSessionStatus = preferencesUserLoginUseCase.getUserSessionStatus()
                _sessionState.value = SessionState.UserSession(userSessionStatus)
            } catch (e: Exception) {
                // Lidar com possíveis erros ao buscar o status da sessão
                // Você pode querer um novo estado para erros, ex: SessionState.Error(e.message)
                _sessionState.value = SessionState.UserSession(false) // Ou um estado de erro apropriado
            }
        }
    }
}
