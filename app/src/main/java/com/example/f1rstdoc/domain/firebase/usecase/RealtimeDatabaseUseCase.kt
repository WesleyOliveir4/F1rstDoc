package com.example.f1rstdoc.domain.firebase.usecase

import com.example.f1rstdoc.domain.docs.model.Docs
import com.example.f1rstdoc.domain.firebase.model.RealtimeDatabaseResult

interface RealtimeDatabaseUseCase {
    suspend fun saveDocsRealtime(
        listDocs: List<Docs>,
        userUid: String,
        result: (RealtimeDatabaseResult<Boolean>) -> Unit
    )
}