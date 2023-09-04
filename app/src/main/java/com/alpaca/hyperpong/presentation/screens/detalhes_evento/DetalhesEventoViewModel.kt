package com.alpaca.hyperpong.presentation.screens.detalhes_evento

import androidx.lifecycle.ViewModel
import com.alpaca.hyperpong.domain.model.Evento
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class DetalhesEventoViewModel: ViewModel() {
    private val _database = Firebase.database.reference
    private val _evento: MutableStateFlow<Evento?> =
        MutableStateFlow(null)
    val evento = _evento.asStateFlow()

    fun getEvento(id: String) {
        _database.child("events").child(id).get().addOnSuccessListener { retornoApi ->
            _evento.value = retornoApi.getValue(Evento::class.java)
        }.addOnFailureListener {
            //TODO...
        }
    }
}