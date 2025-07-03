package com.example.f1rstdoc.data.room.repository

import com.example.f1rstdoc.data.room.dao.DocsDao
import com.example.f1rstdoc.domain.docs.model.Docs
import com.example.f1rstdoc.domain.docs.usecase.DocsRoomDatabaseUseCase
import io.mockk.*
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class DocsRoomDatabaseImplTest {

    private lateinit var dao: DocsDao
    private lateinit var repository: DocsRoomDatabaseUseCase

    @Before
    fun setup() {
        dao = mockk(relaxed = true)
        repository = DocsRoomDatabaseImpl(dao)
    }

    @Test
    fun `getDocs should return flow with list of docs`() = runBlocking {
        // Arrange
        val idUser = "user-123"
        val expectedDocs = listOf(
            Docs(idUser = "Subtitle1", title = "Titulo", subTitle = "Doc content", doc = "teste",date = "2024-01-01")
        )
        val expectedFlow = flowOf(expectedDocs)

        coEvery { dao.getDocs(idUser) } returns expectedFlow

        // Act
        val resultFlow = repository.getDocs(idUser)

        // Assert
        resultFlow.collect { result ->
            assertEquals(expectedDocs, result)
        }
        coVerify { dao.getDocs(idUser) }
    }

    @Test
    fun `insertDocs should call dao with correct docs`() = runBlocking {
        // Arrange
        val fakeDocs = Docs(idUser = "Subtitle1", title = "Titulo", subTitle = "Doc content", doc = "teste",date = "2024-01-01")

        // Act
        repository.insertDocs(fakeDocs)

        // Assert
        coVerify { dao.insertDocs(fakeDocs) }
    }

    @Test
    fun `deleteDocs should call dao with correct id`() = runBlocking {
        // Arrange
        val id = 42

        // Act
        repository.deleteDocs(id)

        // Assert
        coVerify { dao.deleteDocs(id) }
    }

    @Test
    fun `updateDocs should call dao with correct docs`() = runBlocking {
        // Arrange
        val fakeDocs = Docs(idUser = "Subtitle1", title = "Titulo", subTitle = "Doc content", doc = "teste",date = "2024-01-01")

        // Act
        repository.updateDocs(fakeDocs)

        // Assert
        coVerify { dao.updateDocs(fakeDocs) }
    }
}