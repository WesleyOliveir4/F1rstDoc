package com.example.f1rstdoc.domain.internalStorage.usecase

import android.net.Uri
import com.example.f1rstdoc.domain.docs.model.Docs

interface InternalStorageUseCase {

    suspend fun exportData(listDocs: List<Docs>)

    suspend fun selectDataToImport(uri: Uri): Result<List<Docs>>
}