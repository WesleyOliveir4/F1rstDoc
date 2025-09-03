package com.example.f1rstdoc.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.activity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.f1rstdoc.presentation.login.view.LoginActivity
import com.example.f1rstdoc.presentation.splash.screen.SplashScreen
import kotlinx.serialization.Serializable


@Serializable
object HomeRoute

@Serializable
object LoginRoute


@Serializable
object SplashRoute

@Composable
fun SnakeNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = SplashRoute){


        composable<SplashRoute> {
            SplashScreen(
                onNavigateToLogin = { navController.navigate(LoginRoute) }
            )
        }

        activity<LoginRoute> {
            activityClass = LoginActivity::class
        }

    }



}