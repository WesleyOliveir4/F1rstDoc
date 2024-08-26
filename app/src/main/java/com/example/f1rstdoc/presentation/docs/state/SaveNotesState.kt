package com.example.devk.presentation.state

sealed interface SaveNotesState <out T> {
    object Loading: SaveNotesState<Nothing>
    data class Success<out T>(val data: String): SaveNotesState<T>
    data class Failure(val error: String): SaveNotesState<Nothing>
}