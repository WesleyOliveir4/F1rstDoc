package com.example.f1rstdoc.di.sharedpreferences

import com.example.f1rstdoc.data.sharedpreferences.repository.PreferencesUserLoginImpl
import com.example.f1rstdoc.domain.sharedpreferences.usecase.PreferencesUserLoginUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val sharedPreferencesModule = module {

    single<PreferencesUserLoginImpl> {
        PreferencesUserLoginImpl(
            androidContext()
        )
    }

    factory<PreferencesUserLoginUseCase> {
        PreferencesUserLoginImpl(get())
    }
}