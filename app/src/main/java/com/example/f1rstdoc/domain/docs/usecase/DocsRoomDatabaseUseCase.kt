package com.example.f1rstdoc.domain.docs.usecase


import com.example.f1rstdoc.domain.docs.model.Docs
import kotlinx.coroutines.flow.Flow

interface DocsRoomDatabaseUseCase {

    suspend fun getDocs(idUser: String): Flow<List<Docs>>

    suspend fun insertDocs(docs: Docs)

    suspend fun deleteDocs(id:Int)

    suspend fun updateDocs(docs: Docs)
}