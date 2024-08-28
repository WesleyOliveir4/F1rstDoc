package com.example.f1rstdoc.di.login

import com.example.f1rstdoc.presentation.login.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val loginViewModelModule = module {

    viewModel { LoginViewModel(get(),get()) }

}