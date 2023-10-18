package com.alpaca.hyperpong.domain.model

import com.google.firebase.Timestamp

data class Categoria(
    var date: Timestamp? = null,
    var id: String = "",
    var name: String = "",
    var max_participants: Int = 0,
    var price: String = "",
    var participants: HashMap<String, Participante> = hashMapOf()
)
