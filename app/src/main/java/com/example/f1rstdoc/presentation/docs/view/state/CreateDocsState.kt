package com.example.f1rstdoc.presentation.docs.view.state

sealed interface CreateDocsState{
        object Success: CreateDocsState
        object Failure: CreateDocsState
        object Loading: CreateDocsState

}