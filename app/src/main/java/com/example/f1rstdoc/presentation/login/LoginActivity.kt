package com.example.f1rstdoc.presentation.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.f1rstdoc.R
import com.example.f1rstdoc.databinding.ActivityLoginBinding
import com.example.f1rstdoc.domain.firebase.model.FirebaseAuthResult
import com.example.f1rstdoc.presentation.docs.HomeActivity
import com.example.f1rstdoc.presentation.login.viewmodel.LoginViewModel
import com.example.f1rstdoc.presentation.register.RegisterActivity
import com.example.f1rstdoc.presentation.utils.MessageBuilderUtils
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    private val loginViewModel: LoginViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

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
                        loginViewModel.saveUserPrefLogin(email,result.data)

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