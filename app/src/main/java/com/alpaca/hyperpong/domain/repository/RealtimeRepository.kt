package com.alpaca.hyperpong.domain.repository

import androidx.paging.PagingData
import com.alpaca.hyperpong.domain.model.Evento
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

typealias RetornoEventos = Flow<PagingData<Evento>>

interface RealtimeRepository {
    fun getEventos(viewModelScope: CoroutineScope): RetornoEventos
}