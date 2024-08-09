package com.example.f1rstdoc.presentation.register

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.f1rstdoc.databinding.ActivityRegistrarBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterActivity: AppCompatActivity() {

    private val binding by lazy {
        ActivityRegistrarBinding.inflate(layoutInflater)
    }
    private lateinit var mAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        mAuth = Firebase.auth

        registerAct()
    }

    private fun registerAct() {
        binding.btnRegistrar.setOnClickListener {

            val email = binding.emailRegistrar.text.toString()
            val senha = binding.senhaRegistrar.text.toString()

            mAuth.createUserWithEmailAndPassword(
                email,senha
            ).addOnCompleteListener(OnCompleteListener { it ->

                when(it.isSuccessful){
                    true    ->{
                            Toast.makeText(
                                this,
                                "Conta Registrada",
                                Toast.LENGTH_SHORT
                            ).show()
                    }
                    false   ->{
                        var error= ""
                        try {
                            throw it.exception!!
                        } catch (e: FirebaseAuthWeakPasswordException) {
                            error = "A senha deve conter no minimo 6 caracteres"
                        } catch (e: FirebaseAuthInvalidCredentialsException) {
                            error = "E-mail inválido"
                        } catch (e: FirebaseAuthUserCollisionException) {
                            error = "Já existe um cadastro com este e-mail"
                        } catch (e: FirebaseNetworkException) {
                            error = "Sem internet, conecte-se e tente novamente"
                        } catch (e: Exception) {
                            error = "Erro ao efetuar o cadastro"
                            e.printStackTrace()
                        }
                        Toast.makeText(
                            this,
                            "$error",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            })
        }

    }

}