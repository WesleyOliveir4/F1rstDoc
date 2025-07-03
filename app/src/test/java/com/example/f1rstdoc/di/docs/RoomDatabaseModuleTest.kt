package com.example.f1rstdoc.di.docs

import android.content.Context
import com.example.f1rstdoc.data.room.dao.DocsDao
import com.example.f1rstdoc.domain.docs.usecase.DocsRoomDatabaseUseCase
import io.mockk.mockk
import junit.framework.TestCase.assertNotNull
import org.junit.After
import org.junit.Before
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.Test

class RoomDatabaseModuleInjectionTest : KoinTest {

    private val useCase by inject<DocsRoomDatabaseUseCase>()

    @Before
    fun setUp() {
        startKoin {
            modules(
                module {
                    single<DocsDao> {
                        mockk(relaxed = true)
                    }
                },
                roomDatabaseModule

            )
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `should inject DocsRoomDatabaseUseCase successfully`() {
        assertNotNull(useCase)
    }
}
