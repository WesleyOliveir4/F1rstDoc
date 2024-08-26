package com.example.f1rstdoc.domain.docs.usecase

import androidx.lifecycle.LiveData

import com.example.f1rstdoc.domain.docs.model.Docs

interface DocsRoomDatabaseUseCase {

    fun getDocs(): LiveData<List<Docs>>

    fun insertDocs(docs: Docs)

    fun deleteDocs(id:Int)

    fun updateDocs(docs: Docs)
}