package com.example.f1rstdoc.data.sharedpreferences.repository

import android.content.Context
import com.example.f1rstdoc.domain.sharedpreferences.enums.SharedPreferencesIdentifiers
import com.example.f1rstdoc.domain.sharedpreferences.usecase.PreferencesUserLoginUseCase

class PreferencesUserLoginImpl(
    private val context: Context
    ):PreferencesUserLoginUseCase {

    override fun saveUserPref(userLoginSession: Boolean, email: String, userUid: String) {
        val sharedPreferences = context.getSharedPreferences(SharedPreferencesIdentifiers.USER_PREFS.text, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean(SharedPreferencesIdentifiers.IS_LOGGED.text, true)
        editor.putString(SharedPreferencesIdentifiers.USER_UID.text, userUid)
        editor.putString(SharedPreferencesIdentifiers.USER_EMAIL.text, email)
        editor.apply()
    }

    override fun getUserUid(): String {
        val sharedPreferences = context.getSharedPreferences(
            SharedPreferencesIdentifiers.USER_PREFS.text, Context.MODE_PRIVATE
        )
        return sharedPreferences.getString(
            SharedPreferencesIdentifiers.USER_UID.text,
            SharedPreferencesIdentifiers.UNKNOWN.text
        )?:""
    }

    override fun getUserEmail(): String {
        val sharedPreferences = context.getSharedPreferences(
            SharedPreferencesIdentifiers.USER_PREFS.text, Context.MODE_PRIVATE
        )
        return sharedPreferences.getString(
            SharedPreferencesIdentifiers.USER_EMAIL.text,
            SharedPreferencesIdentifiers.UNKNOWN.text
        )?:""
    }
}