package com.example.f1rstdoc.domain.firebase.model

sealed interface RealtimeDatabaseResult<out T>{
        object Success: RealtimeDatabaseResult<Nothing>
        object Failure: RealtimeDatabaseResult<Nothing>

}