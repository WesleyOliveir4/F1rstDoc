package com.example.f1rstdoc.presentation.docs.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.f1rstdoc.domain.docs.model.Docs
import com.example.f1rstdoc.domain.docs.usecase.DocsRoomDatabaseUseCase
import com.example.f1rstdoc.domain.sharedpreferences.enums.SharedPreferencesIdentifiers
import com.example.f1rstdoc.domain.sharedpreferences.usecase.PreferencesUserLoginUseCase
import com.example.f1rstdoc.presentation.docs.state.CreateDocsState
import com.example.f1rstdoc.presentation.docs.state.SaveDocsState
import com.example.f1rstdoc.presentation.utils.factoryDocs

class DocsViewModel(
    private val docsRoomDatabaseUseCase: DocsRoomDatabaseUseCase,
    private val preferencesUserLoginUseCase: PreferencesUserLoginUseCase
) : ViewModel() {

    private val _stateSaveDocs by lazy { MutableLiveData<SaveDocsState<String>>() }
    val stateSaveDocs: LiveData<SaveDocsState<String>> get() = _stateSaveDocs

    private val _stateCreateDocs by lazy { MutableLiveData<CreateDocsState<String>>() }
    val stateCreateDocs: LiveData<CreateDocsState<String>> get() = _stateCreateDocs

    fun getDocs(): LiveData<List<Docs>> {
        val userId = preferencesUserLoginUseCase.getUserId(
            SharedPreferencesIdentifiers.USER_ID.text
        )
        return docsRoomDatabaseUseCase.getDocs(userId)
    }

    fun deleteDocs(id: Int) {
        docsRoomDatabaseUseCase.deleteDocs(id)
    }

    fun createDocs(title: String, subTitle: String, doc: String) {
        if (title.isNotEmpty() || subTitle.isNotEmpty() || doc.isNotEmpty()) {
            val userId = preferencesUserLoginUseCase.getUserId(
                SharedPreferencesIdentifiers.USER_ID.text
            )
            docsRoomDatabaseUseCase.insertDocs(
                factoryDocs(title, subTitle, doc, userId, null)
            )
            _stateCreateDocs.postValue(CreateDocsState.Success)
        } else {
            _stateCreateDocs.postValue(CreateDocsState.Failure)
        }
    }

    fun updateDocs(title: String, subTitle: String, doc: String, id: Int) {
        val userId = preferencesUserLoginUseCase.getUserId(
            SharedPreferencesIdentifiers.USER_ID.text
        )
        docsRoomDatabaseUseCase.updateDocs(factoryDocs(title, subTitle, doc, userId, id))
    }

    fun writeToFile(listNotes: List<Docs>) {
        //            storageNotesUseCase.formatToTXT(listNotes)
    }

    fun saveRealDatabase(listNotes: List<Docs>) {
        //           realDatabaseUseCase.saveNotesDB(listNotes, {_stateSaveNote.value = it } )
    }

}