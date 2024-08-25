package com.example.f1rstdoc

import android.app.Application
import com.example.f1rstdoc.di.firebase.firebaseModule
import com.example.f1rstdoc.di.login.loginViewModelModule
import com.example.f1rstdoc.di.register.registerViewModelModule
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class AppApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            modules(
                firebaseModule,
                registerViewModelModule,
                loginViewModelModule
            )
        }
    }
}