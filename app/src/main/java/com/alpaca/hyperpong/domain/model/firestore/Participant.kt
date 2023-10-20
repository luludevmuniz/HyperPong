package com.alpaca.hyperpong.domain.model.firestore

data class Participant(
    var birth_date: String = "",
    var id: String = "",
    var name: String = "",
    var payment_status: Int = 0,
    var phone: String = ""
)
