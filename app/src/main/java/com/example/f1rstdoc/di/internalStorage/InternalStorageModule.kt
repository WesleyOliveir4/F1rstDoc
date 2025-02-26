package com.example.f1rstdoc.di.internalStorage

import com.example.f1rstdoc.data.internalStorage.repository.InternalStorageImpl
import com.example.f1rstdoc.domain.internalStorage.usecase.InternalStorageUseCase
import org.koin.dsl.module

val internalStorageModule = module {

    factory<InternalStorageUseCase>  {
        InternalStorageImpl()
    }

}