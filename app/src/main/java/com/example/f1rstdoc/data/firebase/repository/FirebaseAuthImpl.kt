package com.example.f1rstdoc.data.firebase.repository

import com.example.f1rstdoc.data.firebase.identifiers.FirebaseAuthIdentifier
import com.example.f1rstdoc.domain.firebase.model.FirebaseAuthResult
import com.example.f1rstdoc.domain.firebase.usecase.FirebaseAuthUseCase
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class FirebaseAuthImpl : FirebaseAuthUseCase {
    private var mAuth: FirebaseAuth = Firebase.auth


    override suspend fun singIn(
        email: String,
        senha: String,
        result: (FirebaseAuthResult<String>) -> Unit
    ) {
        mAuth.signInWithEmailAndPassword(email,senha).addOnCompleteListener{ task ->
            when (task.isSuccessful) {
                true -> {
                    result.invoke(FirebaseAuthResult.Success(data = mAuth.uid!!))
                }
                else -> {
                    result.invoke(FirebaseAuthResult.Error(exception = FirebaseAuthIdentifier.ERROR_GENERIC_SING_IN.toString()))
                }
            }
        }
    }

    override suspend fun createUser(email: String, senha: String, result: (FirebaseAuthResult<Boolean>) -> Unit) {

        mAuth.createUserWithEmailAndPassword(
            email, senha
        ).addOnCompleteListener{ task ->

            when (task.isSuccessful) {
                true -> {
                    result.invoke(FirebaseAuthResult.Success(data = task.isSuccessful))
                }
                false -> {
                    val error: String
                    try {
                        throw task.exception!!
                    } catch (e: FirebaseAuthWeakPasswordException) {
                        error = FirebaseAuthIdentifier.ERROR_MIN_SIX_CHAR.text
                        result.invoke(FirebaseAuthResult.Error(error))
                    } catch (e: FirebaseAuthInvalidCredentialsException) {
                        error = FirebaseAuthIdentifier.ERROR_EMAIL_INVALID.text
                        result.invoke(FirebaseAuthResult.Error(error))
                    } catch (e: FirebaseAuthUserCollisionException) {
                        error = FirebaseAuthIdentifier.ERROR_EMAIL_USED.text
                        result.invoke(FirebaseAuthResult.Error(error))
                    } catch (e: FirebaseNetworkException) {
                        error = FirebaseAuthIdentifier.ERROR_NETWORK_CONNECTION.text
                        result.invoke(FirebaseAuthResult.Error(error))
                    } catch (e: Exception) {
                        error = FirebaseAuthIdentifier.ERROR_GENERIC_CREATE_USER.text
                        result.invoke(FirebaseAuthResult.Error(error))
                    }

                }
            }

        }


    }


}