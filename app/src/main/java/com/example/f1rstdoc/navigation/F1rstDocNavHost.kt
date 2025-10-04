package com.example.f1rstdoc.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.activity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.f1rstdoc.presentation.docs.screen.HomeScreen
import com.example.f1rstdoc.presentation.docs.view.HomeActivity
import com.example.f1rstdoc.presentation.login.screen.LoginScreen
import com.example.f1rstdoc.presentation.register.screen.RegisterScreen
import com.example.f1rstdoc.presentation.splash.screen.SplashScreen
import kotlinx.serialization.Serializable


@Serializable
object HomeRoute

@Serializable
object LoginRoute

@Serializable
object RegisterRoute

@Serializable
object SplashRoute

@Composable
fun SnakeNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = SplashRoute){


        composable<SplashRoute> {
            SplashScreen(
                onNavigateToLogin = { navController.navigate(LoginRoute) },
                onNavigateToHome = { navController.navigate(HomeRoute) }
            )
        }

        composable<LoginRoute> {
            LoginScreen(
                onNavigateToHome = { navController.navigate(HomeRoute) } ,
                onNavigateToRegister = { navController.navigate(RegisterRoute) }
            )
        }


        composable<RegisterRoute> {
            RegisterScreen (
                onNavigateToLogin = { navController.navigate(LoginRoute) }
            )
        }


        composable<HomeRoute> {
            HomeScreen()
        }

//        activity<HomeRoute> {
//            activityClass = HomeActivity::class
//        }

    }



}