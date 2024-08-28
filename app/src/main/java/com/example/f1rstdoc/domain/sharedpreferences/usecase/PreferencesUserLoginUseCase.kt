package com.example.f1rstdoc.domain.sharedpreferences.usecase

interface PreferencesUserLoginUseCase {

    fun saveUserPref(userLoginSession:Boolean,userId: String)

    fun getUserId(userId: String): String
}