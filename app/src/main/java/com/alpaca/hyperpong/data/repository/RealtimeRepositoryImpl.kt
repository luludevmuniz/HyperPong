package com.alpaca.hyperpong.data.repository

import com.alpaca.hyperpong.domain.model.Evento
import com.alpaca.hyperpong.domain.repository.RealtimeRepository
import com.alpaca.hyperpong.domain.repository.RetornoEventos
import com.alpaca.hyperpong.util.ERROR_MESSAGE
import com.alpaca.hyperpong.util.Response
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RealtimeRepositoryImpl @Inject constructor(private val database: DatabaseReference) :
    RealtimeRepository {
    override fun getEventos(viewModelScope: CoroutineScope): StateFlow<Response<List<Evento>>> =
        callbackFlow {
            val retornoApi = database.child("events").get().await()

            val eventos = mutableListOf<Evento>()

            for (snapshot in retornoApi.children) {
                val evento = snapshot.getValue(Evento::class.java)
                evento?.let {
                    eventos.add(it)
                }
            }
            trySend(Response.Success(eventos))
            awaitClose()
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            Response.Loading
        )
}