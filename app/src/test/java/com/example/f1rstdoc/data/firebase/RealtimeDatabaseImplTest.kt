package com.example.f1rstdoc.data.firebase

import com.example.f1rstdoc.data.firebase.identifiers.RealtimeIdentifier
import com.example.f1rstdoc.data.firebase.repository.RealtimeDatabaseImpl
import com.example.f1rstdoc.domain.docs.model.Docs
import com.example.f1rstdoc.domain.firebase.model.RealtimeDatabaseResult
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import io.mockk.*
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class RealtimeDatabaseImplTest {

    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    private lateinit var impl: RealtimeDatabaseImpl

    @Before
    fun setUp() {
        mockkStatic(FirebaseDatabase::class)
        database = mockk()
        reference = mockk(relaxed = true)

        every { FirebaseDatabase.getInstance() } returns database
        every { database.reference } returns reference
        every { reference.child(any()) } returns reference

        impl = RealtimeDatabaseImpl()
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `saveDocsRealtime should return Success when userUid is valid`() = runTest {
        // Arrange
        val userUid = "123"
        val listDocs = listOf(Docs(idUser = "Subtitle1", title = "Titulo", subTitle = "Doc content", doc = "teste",date = "2024-01-01"))

        var result: RealtimeDatabaseResult? = null

        // Act
        impl.saveDocsRealtime(listDocs, userUid) {
            result = it
        }

        // Assert
        assertIs<RealtimeDatabaseResult.Success>(result)
        verify {
            reference.child(RealtimeIdentifier.DOCS.text)
            reference.child(userUid)
            reference.setValue(listDocs)
        }
    }

    @Test
    fun `saveDocsRealtime should return Failure when userUid is empty`() = runTest {
        // Arrange
        val userUid = ""
        val listDocs = emptyList<Docs>()
        var result: RealtimeDatabaseResult? = null

        // Act
        impl.saveDocsRealtime(listDocs, userUid) {
            result = it
        }

        // Assert
        assertEquals(RealtimeDatabaseResult.Failure, result)
        verify(exactly = 0) { reference.setValue(any()) }
    }

    @Test
    fun `saveDocsRealtime should return Failure when Firebase throws exception`() = runTest {
        // Arrange
        val userUid = "456"
        val listDocs = listOf(Docs(idUser = "Subtitle1", title = "Titulo", subTitle = "Doc content", doc = "teste",date = "2024-01-01"))

        every { reference.setValue(any()) } throws RuntimeException("Firebase error")

        var result: RealtimeDatabaseResult? = null

        // Act
        impl.saveDocsRealtime(listDocs, userUid) {
            result = it
        }

        // Assert
        assertEquals(RealtimeDatabaseResult.Failure, result)
        verify {
            reference.child(RealtimeIdentifier.DOCS.text)
            reference.child(userUid)
            reference.setValue(listDocs)
        }
    }
}
