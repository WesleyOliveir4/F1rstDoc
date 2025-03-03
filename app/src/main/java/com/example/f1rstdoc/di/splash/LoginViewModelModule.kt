package com.example.f1rstdoc.di.splash

import com.example.f1rstdoc.presentation.splash.viewmodel.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val splashModule = module {

    viewModel { SplashViewModel(get()) }

}