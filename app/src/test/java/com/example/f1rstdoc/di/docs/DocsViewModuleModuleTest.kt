package com.example.f1rstdoc.di.docs

import com.example.f1rstdoc.domain.docs.usecase.DocsRoomDatabaseUseCase
import com.example.f1rstdoc.domain.firebase.usecase.RealtimeDatabaseUseCase
import com.example.f1rstdoc.domain.internalStorage.usecase.InternalStorageUseCase
import com.example.f1rstdoc.domain.sharedpreferences.usecase.PreferencesUserLoginUseCase
import com.example.f1rstdoc.presentation.docs.viewmodel.DocsViewModel
import io.mockk.mockk
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.assertNotNull

class DocsViewModelInjectionTest : KoinTest {

    private val viewModel by inject<DocsViewModel>()

    @Before
    fun setUp() {
        startKoin {
            modules(
                module {
                    single<DocsRoomDatabaseUseCase> { mockk(relaxed = true) }
                    single<PreferencesUserLoginUseCase> { mockk(relaxed = true) }
                    single<RealtimeDatabaseUseCase> { mockk(relaxed = true) }
                    single<InternalStorageUseCase> { mockk(relaxed = true) }
                },
                docsViewModelModule
            )
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `should inject DocsViewModel successfully`() {
        assertNotNull(viewModel)
    }
}