package com.example.f1rstdoc.data.firebase.repository

import com.example.f1rstdoc.data.firebase.identifiers.RealtimeIdentifier
import com.example.f1rstdoc.domain.docs.model.Docs
import com.example.f1rstdoc.domain.firebase.model.RealtimeDatabaseResult
import com.example.f1rstdoc.domain.firebase.usecase.RealtimeDatabaseUseCase
import com.google.firebase.database.FirebaseDatabase

class RealtimeDatabaseImpl():RealtimeDatabaseUseCase {

    override suspend fun saveDocsRealtime(
        listDocs: List<Docs>,
        userUid: String,
        result: (RealtimeDatabaseResult<Boolean>) -> Unit
    ) {
        when(userUid.isNotEmpty()){
            true->{
                try {
                    FirebaseDatabase.getInstance().reference.child(RealtimeIdentifier.DOCS.text).child(userUid).setValue(listDocs)
                    result.invoke(RealtimeDatabaseResult.Success)
                }catch (e:Exception){
                    result.invoke(RealtimeDatabaseResult.Failure)
                }
            }
            false->{
                result.invoke(RealtimeDatabaseResult.Failure)
            }
        }

    }

}