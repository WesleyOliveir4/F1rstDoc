package com.example.f1rstdoc.data.sharedpreferences.repository

import android.content.Context
import com.example.f1rstdoc.domain.sharedpreferences.enums.SharedPreferencesIdentifiers
import com.example.f1rstdoc.domain.sharedpreferences.usecase.PreferencesUserLoginUseCase

class PreferencesUserLoginImpl(
    private val context: Context
    ):PreferencesUserLoginUseCase {

    override fun saveUserPref(userLoginSession: Boolean, userId: String) {
        val sharedPreferences = context.getSharedPreferences(SharedPreferencesIdentifiers.USER_PREFS.text, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean(SharedPreferencesIdentifiers.IS_LOGGED.text, true)
        editor.apply()
    }

    override fun getUserId(userId: String): String {
        val sharedPreferences = context.getSharedPreferences(
            SharedPreferencesIdentifiers.USER_PREFS.text, Context.MODE_PRIVATE
        )
        return sharedPreferences.getString(
            SharedPreferencesIdentifiers.USER_ID.text,
            SharedPreferencesIdentifiers.UNKNOWN.text
        )?:""
    }
}