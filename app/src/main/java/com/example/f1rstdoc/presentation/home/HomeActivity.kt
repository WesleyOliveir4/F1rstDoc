package com.example.f1rstdoc.presentation.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.f1rstdoc.databinding.ActivityHomeBinding

class HomeActivity: AppCompatActivity() {

    private val binding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

}