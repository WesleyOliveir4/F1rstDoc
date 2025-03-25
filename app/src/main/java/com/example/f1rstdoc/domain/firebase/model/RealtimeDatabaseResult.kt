package com.example.f1rstdoc.domain.firebase.model

sealed interface RealtimeDatabaseResult{
        object Success: RealtimeDatabaseResult
        object Failure: RealtimeDatabaseResult
        object Loading: RealtimeDatabaseResult
}