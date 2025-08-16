package com.example.f1rstdoc.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.activity.compose.setContent
import com.example.f1rstdoc.navigation.SnakeNavHost
import com.example.f1rstdoc.presentation.theme.F1rstDocComposeTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            F1rstDocComposeTheme {
                SnakeNavHost()
            }
        }
    }

}