package com.example.f1rstdoc.presentation.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.f1rstdoc.databinding.ActivitySplashBinding
import com.example.f1rstdoc.presentation.login.LoginActivity

class SplashActivity: AppCompatActivity() {

    private val binding by lazy {
        ActivitySplashBinding.inflate(layoutInflater)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        },2000)
    }

}