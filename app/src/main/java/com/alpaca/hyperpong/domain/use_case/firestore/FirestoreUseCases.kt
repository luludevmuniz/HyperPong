package com.alpaca.hyperpong.domain.use_case.firestore

import com.alpaca.hyperpong.domain.use_case.firestore.get_event.GetEventUseCase
import com.alpaca.hyperpong.domain.use_case.firestore.get_eventos.GetEventosUseCase

data class FirestoreUseCases(
    val getEventosUseCase: GetEventosUseCase,
    val getEventUseCase: GetEventUseCase
)
