package com.example.f1rstdoc.presentation.docs.screen

import androidx.compose.animation.core.copy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.f1rstdoc.R
import com.example.f1rstdoc.domain.docs.model.Docs
import com.example.f1rstdoc.presentation.docs.view.state.GetDocsState
import com.example.f1rstdoc.presentation.docs.viewmodel.DocsViewModel
import com.example.f1rstdoc.presentation.theme.F1rstDocComposeTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(){
    F1rstDocComposeTheme {
        Surface(
            modifier = Modifier.fillMaxWidth(),
        ) {
            val viewModel : DocsViewModel = koinViewModel()
            HomeContent(viewModel)
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(viewModel: DocsViewModel) {

    var showMenu by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = colorResource(id = R.color.principal),
        topBar = {
            TopAppBar(
                title = { Text("F1rstDoc") }, // Defina o título da sua tela
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(id = R.color.meio), // Cor de fundo da TopAppBar
                    titleContentColor = MaterialTheme.colorScheme.onPrimary, // Cor do título
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary // Cor dos ícones de ação
                ),
                actions = {
                    // 1. Ícone de Busca
                    IconButton(onClick = { /* TODO: Implementar lógica de busca */ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_search_view),
                            contentDescription = "SearchDocs"
                        )
                    }

                    // 2. Ícone de Menu com Dropdown
                    Box {
                        IconButton(onClick = { showMenu = !showMenu }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_menu),
                                contentDescription = "MenuHome"
                            )
                        }
                        // Menu que aparece quando showMenu é true
                        DropdownMenu(
                            expanded = showMenu,
                            onDismissRequest = { showMenu = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Cloud Firebase") },
                                onClick = {
                                    /* TODO: Lógica para Cloud */
                                    showMenu = false
                                },
                                leadingIcon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_cloud_firebase),
                                        contentDescription = null
                                    )
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Exportar") },
                                onClick = {
                                    /* TODO: Lógica para Exportar */
                                    showMenu = false
                                },
                                leadingIcon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_save),
                                        contentDescription = null
                                    )
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Importar") },
                                onClick = {
                                    /* TODO: Lógica para Importar */
                                    showMenu = false
                                },
                                leadingIcon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_import_docs),
                                        contentDescription = null
                                    )
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Logout") },
                                onClick = {
                                    /* TODO: Lógica para Logout */
                                    showMenu = false
                                },
                                leadingIcon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_logout),
                                        contentDescription = null
                                    )
                                }
                            )
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {

                },
                // Use a cor 'meio' do seu arquivo de cores XML
                // Certifique-se de que a cor 'meio' esteja definida em res/values/colors.xml
                // containerColor = colorResource(id = R.color.meio)
                // Se 'meio' for uma cor específica, você pode defini-la diretamente:
                containerColor = colorResource(id = R.color.meio) // Substitua pelo valor hexadecimal da cor 'meio'
            ) {
                Icon(
                    imageVector = Icons.Default.Add, // Ícone padrão de Adicionar do Material
                    contentDescription = "Adicionar" // Equivalente ao android:contentDescription
                    // Se você tiver um drawable vetorial personalizado, use:
                    // painter = painterResource(id = R.drawable.ic_adicionar),
                )
            }
        }
    ) { innerPadding ->
        // Conteúdo principal da sua tela vai aqui
        // O padding é fornecido pelo Scaffold para não sobrepor outros elementos
        Box(modifier = Modifier.padding(innerPadding)) {
            LaunchedEffect(Unit) {
                viewModel.getDocs()
            }
            val collectAsGetDocsState = viewModel.stateGetDocs.collectAsState(
                initial = GetDocsState.ListDocs(mutableListOf<Docs>())
            )

            val getDocsstate = collectAsGetDocsState.value
                when (getDocsstate) {
                    is GetDocsState.ListDocs -> {
                        LazyVerticalGrid(
                            modifier = Modifier.fillMaxSize(),
                            columns = GridCells.Fixed(2),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            contentPadding = PaddingValues(16.dp) // Adiciona espaçamento nas bordas da lista
                        ) {
                            items(
                                items = getDocsstate.docs,
                                key = { doc -> doc.id!! } // Chave única para performance
                            ) { doc ->
                                // Composable para cada item da lista
                                DocItem(doc = doc)
                            }
                        }


                    }
                }
        }
    }
}

@Composable
fun DocItem(doc: Docs, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.meio) // Cor de fundo do card
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Exemplo: Exibindo o nome do documento
            // Substitua 'doc.name' pelos campos reais do seu objeto Docs
            Text(
                text = doc.title, // Assumindo que seu objeto Docs tem um campo 'nome'
                style = MaterialTheme.typography.titleMedium,
                color = Color.White
            )
            Text(
                text = "ID: ${doc.subTitle}", // Exemplo com outro campo
                style = MaterialTheme.typography.bodySmall,
                color = Color.White.copy(alpha = 0.7f)
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun HomePreview(){
    F1rstDocComposeTheme {
        Surface(
            modifier = Modifier.fillMaxWidth(),
        ) {
            val viewModel : DocsViewModel = koinViewModel()
            HomeContent(viewModel)
        }
    }
}
