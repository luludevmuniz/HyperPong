package com.alpaca.hyperpong.domain.model

import com.google.firebase.database.PropertyName

data class Categoria(
    @get:PropertyName("maxParticipants") @set:PropertyName("maxParticipants")
    var limiteParticipantes: Int = 0,
    @get:PropertyName("name") @set:PropertyName("name")
    var name: String = "",
    @get:PropertyName("participants") @set:PropertyName("participants")
    var participantes: HashMap<String, Participante> = hashMapOf()
)
