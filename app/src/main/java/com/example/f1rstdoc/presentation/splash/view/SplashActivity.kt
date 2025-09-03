package com.example.f1rstdoc.presentation.splash.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.f1rstdoc.databinding.ActivitySplashBinding
import com.example.f1rstdoc.presentation.docs.view.HomeActivity
import com.example.f1rstdoc.presentation.login.view.LoginActivity
import com.example.f1rstdoc.presentation.splash.state.SessionState
import com.example.f1rstdoc.presentation.splash.viewmodel.SplashViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity: AppCompatActivity() {

    private val binding by lazy {
        ActivitySplashBinding.inflate(layoutInflater)
    }

    private val splashViewModel: SplashViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupRedirectScreen()

    }

    private fun setupRedirectScreen() {
        splashViewModel.fetchUserSession()
        splashViewModel.stateSessionState.observe(this, Observer { state ->

            when(state){
                is SessionState.UserSession -> {
                    if(state.isLogged){
                        redirectToHome()
                    }else{
                        redirectToLogin()
                    }
                }

                SessionState.Loading -> {

                }
            }
        })
    }

    private fun redirectToLogin() {
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        },2000)
    }

    private fun redirectToHome() {
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        },2000)
    }

}