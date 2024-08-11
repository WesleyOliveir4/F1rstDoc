package com.example.f1rstdoc.presentation.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.f1rstdoc.databinding.ActivityLoginBinding
import com.example.f1rstdoc.presentation.home.HomeActivity
import com.example.f1rstdoc.presentation.register.RegisterActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity: AppCompatActivity() {

    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    private lateinit var mAuth: FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        mAuth = Firebase.auth

        redirectToRegister()

        login()
    }

    private fun login() {
        binding.btnEntrar.setOnClickListener {
            val senha = binding.senhalogin.text.toString()
            val email = binding.emaillogin.text.toString()

            mAuth.signInWithEmailAndPassword(email,senha).addOnCompleteListener(OnCompleteListener {task ->

            when(task.isSuccessful){
                true -> {
                    Toast.makeText(
                        this,
                        "Logado",
                        Toast.LENGTH_SHORT
                    ).show()
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                }
                false -> {
                    Toast.makeText(
                        this,
                        "email ou senha invalidos",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            })
        }
    }

    private fun redirectToRegister() {
        binding.btnCriarConta.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}