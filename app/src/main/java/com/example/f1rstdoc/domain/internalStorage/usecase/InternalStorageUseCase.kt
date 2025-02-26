package com.example.f1rstdoc.domain.internalStorage.usecase

import com.example.f1rstdoc.domain.docs.model.Docs

interface InternalStorageUseCase {

    fun exportToPDF(listDocs: List<Docs>)
}