package com.alpaca.hyperpong.presentation.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.alpaca.hyperpong.domain.model.Evento

@Composable
fun HomeFrontLayer(
    modifier: Modifier = Modifier,
    categoria: HomeTab,
    proximosEventos: LazyPagingItems<Evento>,
    eventosConcluidos: LazyPagingItems<Evento>,
    isNetworkAvailable: Boolean,
    onItemClicked: (String) -> Unit
) {
    val itensCarregados by lazy {
        mutableStateOf(false)
    }

    if (isNetworkAvailable) {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.secondaryContainer)
                .padding(
                    start = 24.dp, end = 24.dp, top = 32.dp
                )
        ) {
            item {
                Text(
                    modifier = Modifier.padding(bottom = 16.dp),
                    text = "Próximos Eventos",
                    fontSize = 16.sp
                )
            }

            items(count = proximosEventos.itemCount) { index ->
                itensCarregados.value = true
                EventoItem(evento = proximosEventos[index], onItemClicked = { idEvento ->
                    onItemClicked(idEvento)
                })
            }

            when (proximosEventos.loadState.prepend) {
                is LoadState.Loading -> {
                    item {
                        repeat(2) {
                            ShimmerEffect()
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }

                is LoadState.Error -> {
                    item {
                        ItemEventoErro {
                            proximosEventos.retry()
                        }
                    }
                }

                else -> {}
            }

            when (proximosEventos.loadState.append) {
                is LoadState.Loading -> {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }

                is LoadState.Error -> {
                    item {
                        ItemEventoErro {
                            proximosEventos.retry()
                        }
                    }
                }

                else -> {}
            }

            when (proximosEventos.loadState.refresh) {
                is LoadState.Loading -> {
                    item {
                        repeat(2) {
                            ShimmerEffect()
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }

                is LoadState.Error -> {
                    item {
                        ItemEventoErro {
                            proximosEventos.retry()
                        }
                    }
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
                EventoItem(evento = eventosConcluidos[index], onItemClicked = { idEvento ->
                    onItemClicked(idEvento)
                })
            }

            when (eventosConcluidos.loadState.prepend) {
                is LoadState.Loading -> {
                    item {
                        repeat(2) {
                            ShimmerEffect()
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }

                is LoadState.Error -> {
                    item {
                        ItemEventoErro {
                            eventosConcluidos.retry()
                        }
                    }
                }

                is LoadState.NotLoading -> {}
                else -> {}
            }

            when (eventosConcluidos.loadState.append) {
                is LoadState.Loading -> {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }

                is LoadState.Error -> {
                    item {
                        ItemEventoErro {
                            eventosConcluidos.retry()
                        }
                    }
                }

                is LoadState.NotLoading -> {}
                else -> {}
            }

            when (eventosConcluidos.loadState.refresh) {
                is LoadState.Loading -> {
                    item {
                        repeat(2) {
                            ShimmerEffect()
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }

                is LoadState.Error -> {
                    item {
                        ItemEventoErro {
                            eventosConcluidos.retry()
                        }
                    }
                }

                is LoadState.NotLoading -> {}
                else -> {}
            }
        }
    } else {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.secondaryContainer)
                .padding(
                    start = 24.dp, end = 24.dp, top = 32.dp
                )
        ) {
            Text(text = "Sem conexão disponível")
        }
    }
}

@Composable
fun ItemEventoErro(onRetryClicked: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(30))
            .background(color = MaterialTheme.colorScheme.errorContainer)
            .padding(all = 12.dp)
        ,
        horizontalArrangement = Arrangement.SpaceAround
        ,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Icon(
                tint = MaterialTheme.colorScheme.onErrorContainer,
                imageVector = Icons.Default.Warning,
                contentDescription = null
            )
        }
        Text(
            text = "Ocorreu um erro",
            color = MaterialTheme.colorScheme.onErrorContainer
        )
        OutlinedIconButton(
            modifier = Modifier.size(size = 32.dp),
            border = BorderStroke(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onErrorContainer
            ),
            onClick = { onRetryClicked() }
        ) {
            Icon(
                tint = MaterialTheme.colorScheme.onErrorContainer,
                imageVector = Icons.Default.Refresh,
                contentDescription = "Recarregar itens"
            )
        }
    }
}