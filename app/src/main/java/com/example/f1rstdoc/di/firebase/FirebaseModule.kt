package com.example.f1rstdoc.di.firebase

import com.example.f1rstdoc.data.firebase.repository.FirebaseAuthImpl
import com.example.f1rstdoc.data.firebase.repository.RealtimeDatabaseImpl
import com.example.f1rstdoc.domain.firebase.usecase.FirebaseAuthUseCase
import com.example.f1rstdoc.domain.firebase.usecase.RealtimeDatabaseUseCase
import org.koin.dsl.module

val firebaseModule = module {



    factory<FirebaseAuthUseCase>  {
        FirebaseAuthImpl()
    }

    factory<RealtimeDatabaseUseCase>  {
        RealtimeDatabaseImpl()
    }

}