package com.example.f1rstdoc.presentation.docs.viewmodel

import android.app.Application
import android.text.format.DateFormat
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.f1rstdoc.data.room.dao.DocsDao
import com.example.f1rstdoc.data.room.database.DocsDatabase
import com.example.devk.presentation.state.CreateNotesState
import com.example.devk.presentation.state.SaveNotesState
import com.example.f1rstdoc.domain.docs.model.Docs
import com.example.f1rstdoc.domain.docs.usecase.DocsRoomDatabaseUseCase
import java.util.*

class DocsViewModel(private val docsRoomDatabaseUseCase: DocsRoomDatabaseUseCase) : ViewModel() {

        private val _stateSaveNote by lazy { MutableLiveData<SaveNotesState<String>>() }
        val stateSaveNote: LiveData<SaveNotesState<String>> get()= _stateSaveNote

        private val _stateCreateNotes by lazy { MutableLiveData<CreateNotesState<String>>() }
        val stateCreateNotes: LiveData<CreateNotesState<String>> get()= _stateCreateNotes

    fun factoryDocs(title: String, subTitle: String, doc: String, id: Int?) : Docs {

        val d = Date()
        val notesDate: CharSequence = DateFormat.format("d MMMM yyyy", d.time)

        return Docs(
            id = id,
            title = title,
            subTitle = subTitle,
            doc = doc,
            date = notesDate as String,
        )

    }

    fun getDocs(): LiveData<List<Docs>> {
        return docsRoomDatabaseUseCase.getDocs()
    }

    fun deleteNotes(id: Int) {
        docsRoomDatabaseUseCase.deleteDocs(id)
    }

    fun createNotes(it: View?, title: String, subTitle: String, doc: String){
        if(title.isNotEmpty() || subTitle.isNotEmpty() || doc.isNotEmpty() ){
            docsRoomDatabaseUseCase.insertDocs(factoryDocs(title,subTitle,doc,null))
            _stateCreateNotes.postValue(CreateNotesState.Success("Documento criado"))
        }else{
            _stateCreateNotes.postValue(CreateNotesState.Failure("Falha ao salvar, documento vazio"))
        }
    }

    fun updateDocs(title: String, subTitle: String, doc: String, id: Int){
        docsRoomDatabaseUseCase.updateDocs(factoryDocs(title,subTitle,doc,id))
    }

    fun writeToFile(listNotes: List<Docs>){
//            storageNotesUseCase.formatToTXT(listNotes)
   }
    @Throws(Exception::class)
    fun saveRealDatabase(listNotes: List<Docs>){
//           realDatabaseUseCase.saveNotesDB(listNotes, {_stateSaveNote.value = it } )
    }

}