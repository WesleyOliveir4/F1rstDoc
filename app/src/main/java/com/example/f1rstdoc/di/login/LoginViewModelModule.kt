package com.example.f1rstdoc.di.login

import com.example.f1rstdoc.data.firebase.repository.FirebaseAuthImpl
import com.example.f1rstdoc.domain.firebase.usecase.FirebaseAuthUseCase
import com.example.f1rstdoc.presentation.login.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val loginViewModelModule = module {


    factory<FirebaseAuthUseCase>{
        FirebaseAuthImpl()
    }

    viewModel { LoginViewModel(get()) }

}