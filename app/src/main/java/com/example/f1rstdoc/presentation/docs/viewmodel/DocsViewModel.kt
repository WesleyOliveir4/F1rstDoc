package com.example.f1rstdoc.presentation.docs.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.f1rstdoc.domain.docs.model.Docs
import com.example.f1rstdoc.domain.docs.usecase.DocsRoomDatabaseUseCase
import com.example.f1rstdoc.domain.firebase.model.RealtimeDatabaseResult
import com.example.f1rstdoc.domain.firebase.usecase.RealtimeDatabaseUseCase
import com.example.f1rstdoc.domain.internalStorage.usecase.InternalStorageUseCase
import com.example.f1rstdoc.domain.sharedpreferences.usecase.PreferencesUserLoginUseCase
import com.example.f1rstdoc.presentation.docs.view.state.CreateDocsState
import com.example.f1rstdoc.presentation.docs.view.state.GetDocsState
import com.example.f1rstdoc.presentation.docs.view.state.ImportDocsState
import com.example.f1rstdoc.presentation.utils.factoryDocs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DocsViewModel(
    private val docsRoomDatabaseUseCase: DocsRoomDatabaseUseCase,
    private val preferencesUserLoginUseCase: PreferencesUserLoginUseCase,
    private val realtimeDatabaseUseCase: RealtimeDatabaseUseCase,
    private val internalStorageUseCase: InternalStorageUseCase
) : ViewModel() {

    private val _stateGetDocs  = MutableStateFlow<GetDocsState>(GetDocsState.ListDocs(mutableListOf<Docs>()))
    val stateGetDocs: StateFlow<GetDocsState> = _stateGetDocs.asStateFlow()

    private val _stateCreateDocs  = MutableStateFlow<CreateDocsState>(CreateDocsState.Loading)
    val stateCreateDocs: StateFlow<CreateDocsState> = _stateCreateDocs.asStateFlow()

    private val _stateImportDocs = MutableStateFlow<ImportDocsState>(ImportDocsState.Loading)
    val stateImportDocs: StateFlow<ImportDocsState> = _stateImportDocs

    private val _stateRealtimeResult = MutableStateFlow<RealtimeDatabaseResult>(RealtimeDatabaseResult.Loading)
    val stateRealtimeResult: StateFlow<RealtimeDatabaseResult> = _stateRealtimeResult

    private val userId: String = preferencesUserLoginUseCase.getUserUid()

    fun getDocs() {
        viewModelScope.launch {
            docsRoomDatabaseUseCase.getDocs(userId)
                .flowOn(Dispatchers.IO)
                .collectLatest { listDocs ->
                    _stateGetDocs.value = GetDocsState.ListDocs(listDocs)
                }
        }
    }


    fun deleteDocs(id: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                docsRoomDatabaseUseCase.deleteDocs(id)
            }
        }
    }

    fun createDocs(title: String, subTitle: String, doc: String) {
        if (title.isNotEmpty() || subTitle.isNotEmpty() || doc.isNotEmpty()) {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    docsRoomDatabaseUseCase.insertDocs(
                        factoryDocs(title, subTitle, doc, userId, null)
                    )
                }
            }
            _stateCreateDocs.value = CreateDocsState.Success
        } else {
            _stateCreateDocs.value = CreateDocsState.Failure
        }
    }

    fun updateDocs(title: String, subTitle: String, doc: String, id: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                docsRoomDatabaseUseCase.updateDocs(factoryDocs(title, subTitle, doc, userId, id))
            }
        }
    }

    fun writeToFile(listDocs: List<Docs>) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                internalStorageUseCase.exportData(listDocs)
            }
        }
    }

    fun saveRealDatabase(listDocs: List<Docs>) {
        viewModelScope.launch(Dispatchers.IO){
            realtimeDatabaseUseCase.saveDocsRealtime(listDocs,userId) {
                _stateRealtimeResult.value = it
            }
        }
    }

    fun importDataDocs(selectedUri: Uri) {

        viewModelScope.launch(Dispatchers.IO) {
            internalStorageUseCase.selectDataToImport(selectedUri).fold(
                onSuccess = {result->
                    result.forEach { docs ->
                        docsRoomDatabaseUseCase.insertDocs(
                            factoryDocs(docs.title, docs.subTitle, docs.doc, userId, null)
                        )
                    }
                    _stateImportDocs.value = ImportDocsState.Success

                },
                onFailure = {
                    _stateImportDocs.value = ImportDocsState.Failure
                }
            )
        }

    }

    fun logoutUser(){
        viewModelScope.launch {
            val email = preferencesUserLoginUseCase.getUserEmail()
            val uid = userId
            preferencesUserLoginUseCase.saveUserPref(false,email,uid)
        }
    }

}