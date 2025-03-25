package com.example.f1rstdoc.domain.docs.usecase


import com.example.f1rstdoc.domain.docs.model.Docs
import kotlinx.coroutines.flow.Flow

interface DocsRoomDatabaseUseCase {

    fun getDocs(idUser: String): Flow<List<Docs>>

    fun insertDocs(docs: Docs)

    fun deleteDocs(id:Int)

    fun updateDocs(docs: Docs)
}