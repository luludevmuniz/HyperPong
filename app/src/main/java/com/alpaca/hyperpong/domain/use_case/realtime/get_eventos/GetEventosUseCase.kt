package com.alpaca.hyperpong.domain.use_case.realtime.get_eventos

import com.alpaca.hyperpong.domain.repository.RealtimeRepository
import com.alpaca.hyperpong.domain.repository.RetornoEventos

class GetEventosUseCase(
    private val repository: RealtimeRepository
) {
    operator fun invoke(): RetornoEventos = repository.getEventos()
}