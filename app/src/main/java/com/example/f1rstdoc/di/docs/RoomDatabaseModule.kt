package com.example.f1rstdoc.di.docs

import com.example.f1rstdoc.data.room.dao.DocsDao
import com.example.f1rstdoc.data.room.database.DocsDatabase
import com.example.f1rstdoc.data.room.repository.DocsRoomDatabaseImpl
import com.example.f1rstdoc.domain.docs.usecase.DocsRoomDatabaseUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val roomDatabaseModule = module {

    single<DocsDao>{
        DocsDatabase.getDatabaseInstance(androidContext()).myNotesDao()
    }

    factory<DocsRoomDatabaseUseCase>  {
        DocsRoomDatabaseImpl(get())
    }

}