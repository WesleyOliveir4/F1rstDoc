package com.example.f1rstdoc.domain.firebase.usecase

import com.example.f1rstdoc.domain.firebase.model.FirebaseAuthResult


interface FirebaseAuthUseCase {

    suspend fun singIn(
        email: String,
        senha: String,
        result: (FirebaseAuthResult<String>) -> Unit
    )

    suspend fun createUser(
        email: String,
        senha: String,
        result: (FirebaseAuthResult<Boolean>) -> Unit
    )
}