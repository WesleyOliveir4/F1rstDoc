package com.example.f1rstdoc.di.docs

import com.example.f1rstdoc.presentation.docs.viewmodel.DocsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val docsViewModelModule = module {

    viewModel { DocsViewModel(get(),get()) }

}