package com.alpaca.hyperpong.domain.repository

import com.alpaca.hyperpong.domain.model.Evento
import com.alpaca.hyperpong.util.Response
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow

typealias RetornoEventos = StateFlow<Response<List<Evento>>>

interface RealtimeRepository {
    fun getEventos(viewModelScope: CoroutineScope): RetornoEventos
}