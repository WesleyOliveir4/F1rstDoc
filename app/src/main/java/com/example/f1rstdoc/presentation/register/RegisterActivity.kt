package com.example.f1rstdoc.presentation.register

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.f1rstdoc.databinding.ActivityRegistrarBinding
import com.example.f1rstdoc.domain.firebase.model.FirebaseAuthResult
import com.example.f1rstdoc.presentation.register.viewmodel.RegisterViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityRegistrarBinding.inflate(layoutInflater)
    }

    private val registerViewModel: RegisterViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.setDisplayShowTitleEnabled(false)

    }


    override fun onResume() {
        super.onResume()
        registerAct()
    }

    private fun registerAct() {
        binding.btnRegistrar.setOnClickListener {

            val email = binding.emailRegistrar.text.toString()
            val senha = binding.senhaRegistrar.text.toString()

            registerViewModel.registerUser(email, senha)
        }

        registerViewModel.stateCreateUser.observe(this){ resultCreateUser ->
             when (resultCreateUser) {
                is FirebaseAuthResult.Success -> {
                    Toast.makeText(
                        this,
                        "Conta Registrada",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is FirebaseAuthResult.Error -> {
                    Toast.makeText(
                        this,
                        resultCreateUser.exception,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

    }

}