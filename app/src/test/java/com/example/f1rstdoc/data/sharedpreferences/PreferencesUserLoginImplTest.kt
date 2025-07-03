package com.example.f1rstdoc.data.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import com.example.f1rstdoc.data.sharedpreferences.repository.PreferencesUserLoginImpl
import com.example.f1rstdoc.domain.sharedpreferences.enums.SharedPreferencesIdentifiers
import com.example.f1rstdoc.domain.sharedpreferences.usecase.PreferencesUserLoginUseCase
import io.mockk.*
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class PreferencesUserLoginImplTest {

    private lateinit var context: Context
    private lateinit var sharedPrefs: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var preferences: PreferencesUserLoginUseCase

    @Before
    fun setup() {
        context = mockk(relaxed = true)
        sharedPrefs = mockk(relaxed = true)
        editor = mockk(relaxed = true)

        every {
            context.getSharedPreferences(SharedPreferencesIdentifiers.USER_PREFS.text, Context.MODE_PRIVATE)
        } returns sharedPrefs

        every { sharedPrefs.edit() } returns editor
        every { editor.putBoolean(any(), any()) } returns editor
        every { editor.putString(any(), any()) } returns editor
        every { editor.apply() } just Runs

        preferences = PreferencesUserLoginImpl(context)
    }

    @Test
    fun `saveUserPref should save user session, email, and uid`() {
        // Arrange
        val email = "user@example.com"
        val uid = "123456"
        val isLoggedIn = true

        // Act
        preferences.saveUserPref(isLoggedIn, email, uid)

        // Assert
        verify {
            editor.putBoolean(SharedPreferencesIdentifiers.IS_LOGGED.text, isLoggedIn)
            editor.putString(SharedPreferencesIdentifiers.USER_UID.text, uid)
            editor.putString(SharedPreferencesIdentifiers.USER_EMAIL.text, email)
            editor.apply()
        }
    }

    @Test
    fun `getUserSessionStatus should return true when IS_LOGGED is true`() {
        // Arrange
        every {
            sharedPrefs.getBoolean(SharedPreferencesIdentifiers.IS_LOGGED.text, false)
        } returns true

        // Act
        val result = preferences.getUserSessionStatus()

        // Assert
        assertEquals(true, result)
    }

    @Test
    fun `getUserUid should return stored user uid`() {
        // Arrange
        val expectedUid = "user-uid-123"
        every {
            sharedPrefs.getString(SharedPreferencesIdentifiers.USER_UID.text, SharedPreferencesIdentifiers.UNKNOWN.text)
        } returns expectedUid

        // Act
        val result = preferences.getUserUid()

        // Assert
        assertEquals(expectedUid, result)
    }

    @Test
    fun `getUserEmail should return stored user email`() {
        // Arrange
        val expectedEmail = "user@example.com"
        every {
            sharedPrefs.getString(SharedPreferencesIdentifiers.USER_EMAIL.text, SharedPreferencesIdentifiers.UNKNOWN.text)
        } returns expectedEmail

        // Act
        val result = preferences.getUserEmail()

        // Assert
        assertEquals(expectedEmail, result)
    }
}