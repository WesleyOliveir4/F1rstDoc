package com.example.f1rstdoc.di.register

import com.example.f1rstdoc.data.firebase.repository.FirebaseAuthImpl
import com.example.f1rstdoc.domain.firebase.usecase.FirebaseAuthUseCase
import com.example.f1rstdoc.presentation.register.viewmodel.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val registerViewModelModule = module {


    factory<FirebaseAuthUseCase>{
        FirebaseAuthImpl()
    }

    viewModel { RegisterViewModel(get()) }

}