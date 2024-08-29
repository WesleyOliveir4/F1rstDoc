package com.example.f1rstdoc.presentation.docs.state

sealed interface SaveDocsState <out T> {
    object Loading: SaveDocsState<Nothing>
    data class Success<out T>(val data: String): SaveDocsState<T>
    data class Failure(val error: String): SaveDocsState<Nothing>
}