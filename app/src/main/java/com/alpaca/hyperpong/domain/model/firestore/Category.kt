package com.alpaca.hyperpong.domain.model.firestore

import com.google.firebase.Timestamp

data class Category(
    var date: Timestamp? = null,
    var id: String = "",
    var name: String = "",
    var max_participants: Int = 0,
    var price: String = "",
    var participants: List<Participant> = emptyList()
)
