package com.example.f1rstdoc.presentation.docs.view.state

sealed interface ImportDocsState{
        object Success: ImportDocsState
        object Failure: ImportDocsState
        object Loading: ImportDocsState
}