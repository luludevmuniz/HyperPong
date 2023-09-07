package com.alpaca.hyperpong.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.alpaca.hyperpong.domain.repository.RealtimeRepository
import com.alpaca.hyperpong.domain.repository.RetornoEventos
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class RealtimeRepositoryImpl @Inject constructor(
    private val source: EventosPagingSource,
    private val config: PagingConfig
    ) :
    RealtimeRepository {
    override fun getEventos(viewModelScope: CoroutineScope): RetornoEventos =
        Pager(
            config = config
        ) {
            source
        }.flow
}