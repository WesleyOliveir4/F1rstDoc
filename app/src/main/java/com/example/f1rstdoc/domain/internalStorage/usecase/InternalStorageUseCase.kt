package com.example.f1rstdoc.domain.internalStorage.usecase

import android.net.Uri
import com.example.f1rstdoc.domain.docs.model.Docs

interface InternalStorageUseCase {

    fun exportData(listDocs: List<Docs>)

    fun selectDataToImport(uri: Uri): List<Docs>
}