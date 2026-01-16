package com.example.f1rstdoc.presentation.login.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.f1rstdoc.R
import com.example.f1rstdoc.domain.firebase.model.FirebaseAuthResult
import com.example.f1rstdoc.presentation.login.viewmodel.LoginViewModel
import com.example.f1rstdoc.presentation.theme.F1rstDocComposeTheme
import com.example.f1rstdoc.presentation.utils.MessageBuilderUtils
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    onNavigateToHome: () -> Unit,
    onNavigateToRegister: () -> Unit
){
    F1rstDocComposeTheme {
        Surface(
            modifier = Modifier.fillMaxWidth(),
        ) {

            val viewModel : LoginViewModel = koinViewModel()

            LoginContent(
                viewModel,
                onNavigateToRegister
            )

            var showTransientMessage by remember { mutableStateOf(false) }
            var transientMessageText by remember { mutableStateOf("") }
            var transientMessageType by remember { mutableStateOf(MessageType.INFO) }

            val collectAsState = viewModel.stateLoginAuth.collectAsState(
                initial = FirebaseAuthResult.Loading
            )
            val currentContext = LocalContext.current

            val state = collectAsState.value
            LaunchedEffect(state) {
                when(state){
                    is FirebaseAuthResult.Success -> {
                        showTransientMessage = false
                        viewModel.saveUserPrefLogin(state.email.toString(),state.data)
                        onNavigateToHome()
                    }
                    is FirebaseAuthResult.Error -> {

                        MessageBuilderUtils(currentContext).MessageShow(state.exception)

                        transientMessageText = state.exception ?: "Ocorreu um erro."
                        transientMessageType = MessageType.ERROR
                        showTransientMessage = true
                    }
                    is FirebaseAuthResult.Loading -> {
                        transientMessageText = "Verificando credenciais..." // Mensagem para Loading
                        transientMessageType = MessageType.LOADING
                        showTransientMessage = true
                    }
                }
            }

        }
    }

}

@Composable
fun LoginContent(
    viewModel: LoginViewModel,
    onNavigateToRegister: () -> Unit = {}
) {

    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        contentWindowInsets = WindowInsets.navigationBars,
        containerColor = Color.Red, // Cor de fundo do Scaffold (vermelho)
    ) { paddingValues ->

        Box( // Usamos um Box como container principal para facilitar o alinhamento na parte inferior
            modifier = Modifier
                .fillMaxSize() // O Box preenche todo o Scaffold
                .padding(paddingValues)
        ) {
            // Sua Imagem (opcional, pode estar dentro ou fora do Box inferior)
            // Se quiser a imagem acima do Box branco, coloque-a aqui, alinhada no topo do Box principal.
            // Exemplo:
             Image(
                 modifier = Modifier
                     .align(Alignment.TopCenter) // Alinha a imagem no topo e centro do Box principal
                     .padding(top = 80.dp)
                     .size(width = 100.dp, height = 100.dp),
                 painter = painterResource(id = R.drawable.ic_santander_grey),
                 contentDescription = "F1rstDoc Image",
             )


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.7f) // Ocupa 50% da altura do pai (o Box principal)
                    .align(Alignment.BottomCenter) // Alinha este Box na parte inferior do Box principal
                    .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)) // Bordas arredondadas apenas no topo
                    .background(Color.White) // Cor de fundo do seu "card"
            ) {
                // Conteúdo dentro do seu Box com bordas arredondadas
                // Por exemplo, campos de texto para login, botões, etc.
                // Você pode usar uma Column aqui dentro para organizar os elementos.
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top // Ou outro Arrangement conforme necessário
                ) {
                    // Exemplo de conteúdo
                     Text(
                         modifier = Modifier.padding(top = 5.dp),
                         color = Color.Black,
                         text = "Acessar sua conta"
                     )
                     Spacer(modifier = Modifier.height(16.dp))
                     OutlinedTextField(value = email, onValueChange = { novoValor ->
                         email = novoValor
                     }, label = { Text("Email") })

                     Spacer(modifier = Modifier.height(8.dp))
                     OutlinedTextField(value = senha, onValueChange = { novoValor ->
                         senha = novoValor
                     }, label = { Text("Senha") })


                  Button(
                      modifier = Modifier
                          .padding(top = 250.dp)
                          .size(250.dp, 40.dp),
                      onClick = {
                          viewModel.loginAuth(
                              email = email,
                              senha = senha
                          )
                      },
                      colors = ButtonDefaults.buttonColors(
                          containerColor = Color.Black, // Cor de fundo do botão
                          contentColor = Color.White // Cor do conteúdo (texto/ícone) do botão
                      )
                  ) {
                      Text("Entrar")
                  }

                    Button(
                        modifier = Modifier.padding(top = 4.dp),
                        onClick = {
                            onNavigateToRegister()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White, // Cor de fundo do botão
                            contentColor = Color.Gray // Cor do conteúdo (texto/ícone) do botão
                        )
                    ) {
                        Text("CRIAR CONTA")
                    }

                }
            }

            // Se você quiser que a Imagem fique DENTRO da Column original,
            // e o Box branco por baixo, a estrutura seria diferente.
            // A sua estrutura original era uma Column com a Image dentro.
            // Se for esse o caso, avise para ajustarmos.
            // A sugestão atual coloca a imagem FORA e ACIMA do Box branco.
        }
    }
}


@Composable
@Preview(showBackground = true)
fun LoginPreview(){
    val viewModel : LoginViewModel = koinViewModel()

    F1rstDocComposeTheme {
        LoginContent(viewModel)
    }
}