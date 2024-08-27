package com.example.f1rstdoc.presentation.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.f1rstdoc.R
import com.example.f1rstdoc.presentation.utils.MessageBuilderUtils
import com.example.f1rstdoc.databinding.ActivityLoginBinding
import com.example.f1rstdoc.domain.firebase.model.FirebaseAuthResult
import com.example.f1rstdoc.presentation.docs.HomeActivity
import com.example.f1rstdoc.presentation.login.viewmodel.LoginViewModel
import com.example.f1rstdoc.presentation.register.RegisterActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    private lateinit var mAuth: FirebaseAuth

    private val loginViewModel: LoginViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        mAuth = Firebase.auth

        redirectToRegister()

        login()
    }

    private fun login() {
        binding.btnEntrar.setOnClickListener {
            val email = binding.emaillogin.text.toString()
            val senha = binding.senhalogin.text.toString()


            loginViewModel.loginAuth(email, senha)

            loginViewModel.stateLoginAuth.observe(this) { result ->
                when (result) {
                    is FirebaseAuthResult.Success -> {
                        startActivity(Intent(this, HomeActivity::class.java))
                        finish()
                    }
                    is FirebaseAuthResult.Error -> {
                        MessageBuilderUtils(this).MessageShow(R.string.error_generic_login.toString())
                    }
                }
            }
        }
    }

    private fun redirectToRegister() {
        binding.btnCriarConta.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}