package com.example.f1rstdoc.domain.firebase.model

import java.util.UUID

sealed interface FirebaseAuthResult<out T> {
    data object Loading : FirebaseAuthResult<Nothing>
    data class Success<out T>(val data: T, val email: String? = null) : FirebaseAuthResult<T>
    data class Error(val exception: String, val errorId: String = UUID.randomUUID().toString()) : FirebaseAuthResult<Nothing>
}
