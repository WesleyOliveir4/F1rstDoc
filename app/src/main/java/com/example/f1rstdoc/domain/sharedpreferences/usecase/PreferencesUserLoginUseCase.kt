package com.example.f1rstdoc.domain.sharedpreferences.usecase

interface PreferencesUserLoginUseCase {

    fun saveUserPref(userLoginSession:Boolean,email: String,userUid: String)

    fun getUserUid(): String

    fun getUserEmail(): String
}