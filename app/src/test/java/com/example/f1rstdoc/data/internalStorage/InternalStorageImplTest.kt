package com.example.f1rstdoc.data.internalStorage

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import com.example.f1rstdoc.data.internalStorage.repository.InternalStorageImpl
import com.example.f1rstdoc.domain.docs.model.Docs
import com.example.f1rstdoc.domain.internalStorage.usecase.InternalStorageUseCase
import com.google.gson.Gson
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.ByteArrayInputStream

class InternalStorageImplTest {

    private lateinit var context: Context
    private lateinit var contentResolver: ContentResolver
    private lateinit var internalStorage: InternalStorageUseCase

    @Before
    fun setUp() {
        context = mockk()
        contentResolver = mockk()
        every { context.contentResolver } returns contentResolver
        internalStorage = InternalStorageImpl(context)
    }

    @Test
    fun `selectDataToImport should return success when JSON is valid`() = runBlocking {
        // Arrange
        val uri = mockk<Uri>()
        val fakeDocsList = listOf(Docs(idUser = "Subtitle1", title = "Titulo", subTitle = "Doc content", doc = "teste",date = "2024-01-01"))
        val json = Gson().toJson(fakeDocsList)
        val inputStream = ByteArrayInputStream(json.toByteArray())

        every { contentResolver.openInputStream(uri) } returns inputStream

        // Act
        val result = internalStorage.selectDataToImport(uri)

        // Assert
        assert(result.isSuccess)
        assertEquals(fakeDocsList[0].doc, result.getOrNull()?.get(0)?.doc )
    }

    @Test
    fun `selectDataToImport should return failure when JSON is empty`() = runBlocking {
        // Arrange
        val uri = mockk<Uri>()
        val emptyJson = "[]"
        val inputStream = ByteArrayInputStream(emptyJson.toByteArray())

        every { contentResolver.openInputStream(uri) } returns inputStream

        // Act
        val result = internalStorage.selectDataToImport(uri)

        // Assert
        assert(result.isFailure)
        assert(result.exceptionOrNull()?.message == "Não foi possivel importar a lista de Docs")
    }

    @Test
    fun `selectDataToImport should return failure when file is unreadable`() = runBlocking {
        // Arrange
        val uri = mockk<Uri>()
        every { contentResolver.openInputStream(uri) } returns null

        // Act
        val result = internalStorage.selectDataToImport(uri)

        // Assert
        assert(result.isFailure)
        assert(result.exceptionOrNull()?.message == "Não foi possivel importar a lista de Docs")
    }

}
