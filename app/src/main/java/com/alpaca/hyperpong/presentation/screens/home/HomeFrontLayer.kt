package com.alpaca.hyperpong.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.alpaca.hyperpong.domain.model.Evento
import com.alpaca.hyperpong.util.Response

enum class CategoriaAula {
    Movimentacao, Reflexo, Criatividade
}

data class Aula(
    val nome: String,
    val data: String,
    val hora: String,
    val categoria: CategoriaAula
)

@Composable
fun HomeFrontLayer(
    modifier: Modifier = Modifier,
    categoria: HomeTab,
    loadState: Response<Response.Idle>,
    eventosConcluidos: LazyPagingItems<Evento>,
    proximosEventos: LazyPagingItems<Evento>,
    onItemClicked: (String) -> Unit
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.secondaryContainer)
            .padding(
                start = 24.dp,
                end = 24.dp,
                top = 32.dp
            )
    ) {
        if (categoria == HomeTab.Eventos) {
            item {
                Text(
                    modifier = Modifier.padding(bottom = 16.dp),
                    text = "Próximos Eventos",
                    fontSize = 16.sp
                )
            }
            when(loadState) {
                is Response.Success -> {
                    items(count = proximosEventos.itemCount) { index ->
                        EventoItem(
                            evento = proximosEventos[index]!!,
                            onItemClicked = { idEvento ->
                                onItemClicked(idEvento)
                            }
                        )
                    }
                }
                is Response.Error -> {

                }
                is Response.Loading -> {

                }
                else -> {}
            }
            item {
                Text(
                    modifier = Modifier.padding(top = 24.dp, bottom = 16.dp),
                    text = "Eventos Concluídos",
                    fontSize = 16.sp
                )
            }
            items(count = eventosConcluidos.itemCount) { index ->
                EventoItem(
                    evento = eventosConcluidos[index]!!,
                    onItemClicked = { idEvento ->
                        onItemClicked(idEvento)
                    }
                )
            }
        }
    }
}

@Composable
private fun ListItemAula(aula: Aula) {
    ListItem(
        colors = ListItemDefaults.colors(containerColor = MaterialTheme.colorScheme.onSecondary),
        leadingContent = {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Ícone ${aula.nome}"
            )
        },
        headlineContent = { Text(text = aula.nome) },
        supportingContent = { Text(text = aula.data) },
        trailingContent = {
            Text(text = aula.hora)
        }
    )
    HorizontalDivider()
}
