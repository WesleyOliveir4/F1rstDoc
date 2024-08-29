package com.example.f1rstdoc.presentation.docs.state

sealed interface CreateDocsState<out T>{
        object Success: CreateDocsState<Nothing>
        object Failure: CreateDocsState<Nothing>

}