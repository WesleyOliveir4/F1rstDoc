package com.example.devk.presentation.state

sealed interface CreateNotesState<out T>{
        object Loading: CreateNotesState<Nothing>
        data class Success<out T>(val data: String): CreateNotesState<T>
        data class Failure(val error: String): CreateNotesState<Nothing>

}