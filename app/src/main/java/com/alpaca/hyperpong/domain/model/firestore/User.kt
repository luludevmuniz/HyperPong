package com.alpaca.hyperpong.domain.model.firestore

data class User(
    val name: String = "",
    val document: String = "",
    val email: String = "",
    val paymentId: String = "",
    val phone: String = "",
    val password: String = "",
    var uid: String = "",
)
