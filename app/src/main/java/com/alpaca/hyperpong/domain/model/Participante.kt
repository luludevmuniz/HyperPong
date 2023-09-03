package com.alpaca.hyperpong.domain.model

import com.google.firebase.database.PropertyName

data class Participante(
    @get:PropertyName("dtaNascimento") @set:PropertyName("dtaNascimento")
    var dataNascimento: String = "",
    @get:PropertyName("idParticipants") @set:PropertyName("idParticipants")
    var idParticipante: String = "",
    @get:PropertyName("nomeSobrenome") @set:PropertyName("nomeSobrenome")
    var nomeCompleto: String = "",
    @get:PropertyName("status") @set:PropertyName("status")
    var statusPagamento: Int = 0,
    var telefone: String = ""
)
