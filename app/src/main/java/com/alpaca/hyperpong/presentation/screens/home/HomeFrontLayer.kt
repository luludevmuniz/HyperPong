package com.alpaca.hyperpong.presentation.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.alpaca.hyperpong.R
import com.alpaca.hyperpong.domain.model.Evento

enum class CategoriaAula {
    Movimentacao, Reflexo, Criatividade
}

data class Aula(
    val nome: String,
    val data: String,
    val hora: String,
    val categoria: CategoriaAula
)

val aulas = listOf(
    Aula(
        nome = "Aula Coletiva",
        data = "24 de julho",
        hora = "19h - 20:30h",
        categoria = CategoriaAula.Movimentacao
    ),
    Aula(
        nome = "Aula Particular",
        data = "26 de julho",
        hora = "18h - 19h",
        categoria = CategoriaAula.Reflexo
    ),
    Aula(
        nome = "Aula Coletiva",
        data = "26 de julho",
        hora = "19h - 20:30h",
        categoria = CategoriaAula.Criatividade
    )
)


@Composable
fun HomeFrontLayer(
    modifier: Modifier = Modifier,
    eventos: List<Evento> = emptyList(),
    aulas: List<Aula> = emptyList(),
    categoria: HomeTab,
    onItemClicked: (String) -> Unit
) {
    val isTelaEventos = categoria == HomeTab.Eventos
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.secondaryContainer
    ) {
        Column(
            modifier = Modifier.padding(
                start = 24.dp,
                end = 24.dp,
                top = 16.dp
            )
        ) {
            Text(text = if (isTelaEventos) "Próximos Eventos" else "Próximas Aulas")
            Spacer(Modifier.height(16.dp))
            Box(modifier = Modifier.weight(1f)) {
                val listState = rememberLazyListState()
                LazyColumn(state = listState) {
                    if (isTelaEventos) {
                        items(eventos) { evento ->
                            ListItemEvento(
                                evento = evento,
                                onItemClicked = { idEvento ->
                                    onItemClicked(idEvento)
                                }
                            )
                        }
                    } else {
                        items(aulas) { aula ->
                            ListItemAula(aula)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ListItemEvento(evento: Evento, onItemClicked: (String) -> Unit) {
    ListItem(
        modifier = Modifier.clickable { onItemClicked(evento.id) },
        colors = ListItemDefaults.colors(containerColor = MaterialTheme.colorScheme.onSecondary),
        leadingContent = {
            AsyncImage(
                modifier = Modifier.sizeIn(maxHeight = 56.dp, maxWidth = 56.dp),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(evento.imagem)
                    .crossfade(true)
                    .build(),
                contentScale = ContentScale.FillBounds,
                contentDescription = "Imagem do evento ${evento.nome}"
            )
        },
        headlineContent = { Text(text = evento.nome) },
        supportingContent = { Text(text = evento.dataInicio) },
        trailingContent = {
            Icon(
                imageVector = Icons.Outlined.Add,
                contentDescription = "Ícone Inscrições Abertas"
            )
        }
    )
    HorizontalDivider()
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
