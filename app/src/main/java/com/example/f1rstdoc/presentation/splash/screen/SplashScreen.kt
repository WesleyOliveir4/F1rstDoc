package com.example.f1rstdoc.presentation.splash.screen

import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.f1rstdoc.R
import com.example.f1rstdoc.presentation.splash.state.SessionState
import com.example.f1rstdoc.presentation.splash.viewmodelcompose.SplashViewModel
import com.example.f1rstdoc.presentation.theme.F1rstDocComposeTheme
import org.koin.androidx.compose.koinViewModel


@Composable
fun SplashScreen(onNavigateToLogin: () -> Unit) {
    F1rstDocComposeTheme {
        Surface(
            modifier = Modifier.fillMaxWidth(),
        ) {
            val splashViewModel: SplashViewModel = koinViewModel()
            splashViewModel.fetchUserSession()
            val sessionState by splashViewModel.sessionState.collectAsState()

            LaunchedEffect(Unit) {
                splashViewModel.fetchUserSession()
            }

            when (val state = sessionState) {
                is SessionState.Loading -> {
                    // Mostrar um indicador de carregamento
                    // Ex: CircularProgressIndicator()
                    // Ou sua animação de Splash
                }
                is SessionState.UserSession -> {
                    // Efeito para navegar quando o estado UserSession for recebido
                    // LaunchedEffect com 'state' como chave garante que isso execute
                    // quando 'state' (especificamente a instância de UserSession) mudar.
                    LaunchedEffect(state) {
                        if (state.isLogged) {
                            Handler(Looper.getMainLooper()).postDelayed({
                                onNavigateToLogin()
                            },2000)
                        } else {
                            Handler(Looper.getMainLooper()).postDelayed({
                                onNavigateToLogin()
                            },2000)
                        }
                    }
                }
                // is SessionState.Error -> {
                //     // Mostrar uma mensagem de erro
                // }
            }

            SplashContent()
        }
    }

}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SplashContent() {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF000000),
                        Color(0xFF272626),
                    )
                )
            )
    ) {
        Scaffold(
            containerColor = Color.Transparent // Importante!
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize() // Ocupa o espaço disponível do Scaffold
                    .padding(paddingValues) // Aplica os paddings internos do Scaffold
                    // .consumeWindowInsets(paddingValues) // consumeWindowInsets é para quando o Scaffold lida com insets
                    .padding(16.dp) // Seu padding de conteúdo
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 100.dp)
                        .size(120.dp, 350.dp),
                    painter = painterResource(id = R.drawable.ic_first_logo),
                    contentDescription = "F1rstDoc Splash Image",
                    alignment = Alignment.BottomCenter
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashPreview() {
    F1rstDocComposeTheme {
        Surface(
            modifier = Modifier.fillMaxWidth(),
        ) {
            SplashContent()
        }
    }

}