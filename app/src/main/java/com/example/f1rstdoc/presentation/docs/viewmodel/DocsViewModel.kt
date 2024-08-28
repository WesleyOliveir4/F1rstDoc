package com.example.f1rstdoc.presentation.docs.viewmodel

import android.text.format.DateFormat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.devk.presentation.state.CreateNotesState
import com.example.devk.presentation.state.SaveNotesState
import com.example.f1rstdoc.domain.docs.model.Docs
import com.example.f1rstdoc.domain.docs.usecase.DocsRoomDatabaseUseCase
import com.example.f1rstdoc.domain.sharedpreferences.enums.SharedPreferencesIdentifiers
import com.example.f1rstdoc.domain.sharedpreferences.usecase.PreferencesUserLoginUseCase
import java.util.*

class DocsViewModel(
    private val docsRoomDatabaseUseCase: DocsRoomDatabaseUseCase,
    private val preferencesUserLoginUseCase: PreferencesUserLoginUseCase
) : ViewModel() {

        private val _stateSaveNote by lazy { MutableLiveData<SaveNotesState<String>>() }
        val stateSaveNote: LiveData<SaveNotesState<String>> get()= _stateSaveNote

        private val _stateCreateNotes by lazy { MutableLiveData<CreateNotesState<String>>() }
        val stateCreateNotes: LiveData<CreateNotesState<String>> get()= _stateCreateNotes

    fun factoryDocs(title: String, subTitle: String, doc: String,idUser:String ,id: Int?) : Docs {

        val d = Date()
        val notesDate: CharSequence = DateFormat.format("d MMMM yyyy", d.time)

        return Docs(
            id = id,
            idUser = idUser,
            title = title,
            subTitle = subTitle,
            doc = doc,
            date = notesDate as String,
        )

    }

        fun getDocs(): LiveData<List<Docs>> {
            val userId = preferencesUserLoginUseCase.getUserId(
                SharedPreferencesIdentifiers.USER_ID.text
            )
            return docsRoomDatabaseUseCase.getDocs(userId)
    }

    fun deleteNotes(id: Int) {
        docsRoomDatabaseUseCase.deleteDocs(id)
    }

    fun createNotes(title: String, subTitle: String, doc: String){
        if(title.isNotEmpty() || subTitle.isNotEmpty() || doc.isNotEmpty() ){
            val userId = preferencesUserLoginUseCase.getUserId(
                SharedPreferencesIdentifiers.USER_ID.text
            )
            docsRoomDatabaseUseCase.insertDocs(factoryDocs(title,subTitle,doc,userId,null))
            _stateCreateNotes.postValue(CreateNotesState.Success("Documento criado"))
        }else{
            _stateCreateNotes.postValue(CreateNotesState.Failure("Falha ao salvar, documento vazio"))
        }
    }

    fun updateDocs(title: String, subTitle: String, doc: String, id: Int){
        val userId = preferencesUserLoginUseCase.getUserId(
            SharedPreferencesIdentifiers.USER_ID.text
        )
        docsRoomDatabaseUseCase.updateDocs(factoryDocs(title,subTitle,doc,userId,id))
    }

    fun writeToFile(listNotes: List<Docs>){
//            storageNotesUseCase.formatToTXT(listNotes)
   }
    @Throws(Exception::class)
    fun saveRealDatabase(listNotes: List<Docs>){
//           realDatabaseUseCase.saveNotesDB(listNotes, {_stateSaveNote.value = it } )
    }

}