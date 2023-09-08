package com.alpaca.hyperpong.domain.use_case.realtime.get_eventos

import com.alpaca.hyperpong.domain.repository.RealtimeRepository
import com.alpaca.hyperpong.domain.repository.RetornoEventos
import kotlinx.coroutines.CoroutineScope

class GetEventosUseCase(
    private val repository: RealtimeRepository
) {
    operator fun invoke(viewModelScope: CoroutineScope): RetornoEventos = repository.getEventos(viewModelScope = viewModelScope)
}