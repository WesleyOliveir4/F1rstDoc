package com.example.f1rstdoc.di.firebase


import com.example.f1rstdoc.domain.firebase.usecase.FirebaseAuthUseCase
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.Test

class FirebaseModuleTest : KoinTest {

    private val useCase by inject<FirebaseAuthUseCase>()

    @Before
    fun setUp() {
        startKoin {
            modules(
                firebaseModule
            )
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    //
    @Test
    fun `should inject FirebaseAuthUseCase successfully`() = runTest {
        assertNotNull(useCase)
    }
}