package com.example.f1rstdoc.data.firebase

import com.example.f1rstdoc.data.firebase.repository.FirebaseAuthImpl
import com.example.f1rstdoc.domain.firebase.model.FirebaseAuthResult
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue

class FirebaseAuthImplTest {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var auth: FirebaseAuthImpl

    @Before
    fun setUp() {
        firebaseAuth = mockk(relaxed = true)
        auth = FirebaseAuthImpl(firebaseAuth)
    }

    @Test
    fun `singIn should return Success when login is successful`() = runTest {
        // Arrange
        val email = "test@example.com"
        val password = "password123"
        val task = mockk<Task<AuthResult>>()
        val slot = slot<OnCompleteListener<AuthResult>>()

        every { firebaseAuth.signInWithEmailAndPassword(email, password) } returns task
        every { task.addOnCompleteListener(capture(slot)) } returns task
        every { firebaseAuth.uid } returns "uid_123"

        // Act
        var result: FirebaseAuthResult<String>? = null
        auth.singIn(email, password) { res -> result = res }

        slot.captured.onComplete(mockk {
            every { isSuccessful } returns true
        })

        // Assert
        assertIs<FirebaseAuthResult.Success<String>>(result)
        assertTrue((result as FirebaseAuthResult.Success).data == "uid_123")
    }

    @Test
    fun `singIn should return Error when login fails`() = runTest {
        // Arrange
        val email = "fail@example.com"
        val password = "failpass"
        val task = mockk<Task<AuthResult>>()
        val slot = slot<OnCompleteListener<AuthResult>>()

        every { firebaseAuth.signInWithEmailAndPassword(email, password) } returns task
        every { task.addOnCompleteListener(capture(slot)) } returns task

        // Act
        var result: FirebaseAuthResult<String>? = null
        auth.singIn(email, password) { res -> result = res }

        slot.captured.onComplete(mockk {
            every { isSuccessful } returns false
        })

        // Assert
        assertIs<FirebaseAuthResult.Error>(result)
    }

    @Test
    fun `createUser should return Success when creation is successful`() = runTest {
        // Arrange
        val email = "test@example.com"
        val password = "password123"
        val task = mockk<Task<AuthResult>>()
        val slot = slot<OnCompleteListener<AuthResult>>()

        every { firebaseAuth.createUserWithEmailAndPassword(email, password) } returns task
        every { task.addOnCompleteListener(capture(slot)) } returns task

        // Act
        var result: FirebaseAuthResult<Boolean>? = null
        auth.createUser(email, password) { res -> result = res }

        slot.captured.onComplete(mockk {
            every { isSuccessful } returns true
        })

        // Assert
        assertIs<FirebaseAuthResult.Success<Boolean>>(result)
        assertTrue((result as FirebaseAuthResult.Success).data)
    }

    @Test
    fun `createUser should return specific error when email already used`() = runTest {
        // Arrange
        val email = "duplicate@example.com"
        val password = "password123"
        val task = mockk<Task<AuthResult>>()
        val slot = slot<OnCompleteListener<AuthResult>>()

        every { firebaseAuth.createUserWithEmailAndPassword(email, password) } returns task
        every { task.addOnCompleteListener(capture(slot)) } returns task

        val mockException = mockk<FirebaseAuthUserCollisionException>()
        every { mockException.message } returns "Email is already in use"

        // Act
        var result: FirebaseAuthResult<Boolean>? = null
        auth.createUser(email, password) { res -> result = res }

        slot.captured.onComplete(mockk {
            every { isSuccessful } returns false
            every { exception } returns mockException
        })

        // Assert
        assertIs<FirebaseAuthResult.Error>(result)
        assertEquals((result as FirebaseAuthResult.Error).exception ,"Já existe um cadastro com este e-mail")
    }
}