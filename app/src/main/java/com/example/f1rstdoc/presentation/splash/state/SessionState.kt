package com.example.f1rstdoc.presentation.splash.state

sealed class SessionState{

    data class UserSession( val isLogged: Boolean): SessionState()

}
