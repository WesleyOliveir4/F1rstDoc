package com.example.f1rstdoc.domain.firebase.model

sealed interface FirebaseAuthResult<out T> {
    data class Success<out T>(val data: T) : FirebaseAuthResult<T>
    data class Error(val exception: String) : FirebaseAuthResult<Nothing>
}