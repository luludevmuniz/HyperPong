package com.alpaca.hyperpong.presentation.screens.home

import androidx.lifecycle.ViewModel
import com.alpaca.hyperpong.domain.model.Evento
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel : ViewModel() {
    private val _database = Firebase.database.reference
    private val _eventos: MutableStateFlow<List<Evento>> =
        MutableStateFlow(emptyList())
    val eventos = _eventos.asStateFlow()

    init {
        _database.child("events").get().addOnSuccessListener { retornoApi ->
            val eventos = mutableListOf<Evento>()

            for (snapshot in retornoApi.children) {
                val evento = snapshot.getValue(Evento::class.java)
                evento?.let {
                    eventos.add(it)
                }
            }

            _eventos.value = eventos
        }.addOnFailureListener {
            //TODO...
        }
    }
}
