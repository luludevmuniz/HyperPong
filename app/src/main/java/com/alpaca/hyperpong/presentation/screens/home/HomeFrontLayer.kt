package com.alpaca.hyperpong.presentation.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.alpaca.hyperpong.R

val BottomSheetShape = RoundedCornerShape(
    topStart = 20.dp,
    topEnd = 20.dp,
    bottomStart = 0.dp,
    bottomEnd = 0.dp
)

enum class CategoriaAula {
    Movimentacao, Reflexo, Criatividade, Saque
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
        categoria = CategoriaAula.Movimentacao
    )
)

data class Evento(
    val nome: String,
    val data: String,
    val imagem: String,
    val inscricoesAbertas: Boolean
)

val eventos = listOf(
    Evento(
        nome = "Copa Hyper",
        data = "16 e 17 de setembro",
        imagem = "",
        inscricoesAbertas = false
    ),
    Evento(
        nome = "Rachão",
        data = "22 de julho",
        imagem = "",
        inscricoesAbertas = true
    ),
    Evento(
        nome = "Torneio Interno",
        data = "11 e 12 de agosto",
        imagem = "",
        inscricoesAbertas = false
    ),
    Evento(
        nome = "Rachão",
        data = "26 de agosto",
        imagem = "",
        inscricoesAbertas = false
    ),
)

@Composable
fun HomeFrontLayer(
    modifier: Modifier = Modifier,
    eventos: List<Evento> = emptyList(),
    aulas: List<Aula> = emptyList(),
    categoria: HomeTab
) {
    val isTelaEventos = categoria == HomeTab.Eventos
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.secondaryContainer,
        shape = BottomSheetShape
    ) {
        Column(
            modifier = Modifier.padding(
                start = 24.dp,
                end = 24.dp,
                top = 16.dp
            )
        ) {
            Text(
                text = if (isTelaEventos) "Próximos Eventos" else "Próximas Aulas"
            )
            Spacer(Modifier.height(16.dp))
            Box(modifier = Modifier.weight(1f)) {
                val listState = rememberLazyListState()
                LazyColumn(state = listState) {
                    if (isTelaEventos) {
                        items(eventos) { evento ->
                            ListItemEvento(evento)
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
private fun ListItemEvento(evento: Evento) {
    ListItem(
        colors = ListItemDefaults.colors(containerColor = MaterialTheme.colorScheme.onSecondary),
        leadingContent = {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = null
            )
        },
        headlineContent = { Text(text = evento.nome) },
        supportingContent = { Text(text = evento.data) },
        trailingContent = {
            Icon(
                imageVector = Icons.Outlined.Add,
                contentDescription = "Ícone Inscrições Abertas"
            )
        }
    )
    Divider()
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
    Divider()
}
