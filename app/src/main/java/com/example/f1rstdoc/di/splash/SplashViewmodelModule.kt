package com.example.f1rstdoc.di.splash

import com.example.f1rstdoc.presentation.splash.viewmodelcompose.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val splashComposeModule = module {

    viewModel { SplashViewModel(
        get()
    ) }

}