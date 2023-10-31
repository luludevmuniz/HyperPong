package com.alpaca.hyperpong.domain.use_case.firestore

import com.alpaca.hyperpong.domain.use_case.firestore.get_event.GetEventUseCase
import com.alpaca.hyperpong.domain.use_case.firestore.get_eventos.GetEventosUseCase
import com.alpaca.hyperpong.domain.use_case.firestore.get_user.GetUserUseCase

data class FirestoreUseCases(
    val getEventosUseCase: GetEventosUseCase,
    val getEventUseCase: GetEventUseCase,
    val getUserUseCase: GetUserUseCase
)
