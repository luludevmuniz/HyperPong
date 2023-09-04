package com.alpaca.hyperpong.presentation.screens.home

import androidx.lifecycle.ViewModel
import com.alpaca.hyperpong.domain.model.Evento
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class HomeViewModel : ViewModel() {
    private val _database = Firebase.database.reference
    private val _eventos: MutableStateFlow<List<Evento>> =
        MutableStateFlow(emptyList())
    val eventos = _eventos.asStateFlow()
    private val _eventosFuturos: MutableStateFlow<List<Evento>> =
        MutableStateFlow(emptyList())
    val eventosFuturos = _eventosFuturos.asStateFlow()
    private val _eventosConcluidos: MutableStateFlow<List<Evento>> =
        MutableStateFlow(emptyList())
    val eventosConcluidos = _eventosConcluidos.asStateFlow()

    init {
        _database.child("events").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(retornoApi: DataSnapshot) {
                val eventos = mutableListOf<Evento>()

                for (snapshot in retornoApi.children) {
                    val evento = snapshot.getValue(Evento::class.java)
                    evento?.let {
                        eventos.add(it)
                    }
                }

                _eventos.value = eventos
                _eventosFuturos.value = filtrarProximosEventos()
                _eventosConcluidos.value = filtrarEventosConcluidos()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun filtrarEventosConcluidos(): List<Evento> =
        _eventos.value.filter { evento ->
            val dataAPI = LocalDate.parse(evento.dataInicio, DateTimeFormatter.ISO_DATE)
            dataAPI.isBefore(LocalDate.now()) || dataAPI.isEqual(LocalDate.now())
        }

    private fun filtrarProximosEventos(): List<Evento> = _eventos.value.filter { evento ->
        val dataAPI = LocalDate.parse(evento.dataInicio, DateTimeFormatter.ISO_DATE)
        dataAPI.isAfter(LocalDate.now())
    }
}
