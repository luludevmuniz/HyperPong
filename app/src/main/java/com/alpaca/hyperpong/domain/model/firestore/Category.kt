package com.alpaca.hyperpong.domain.model.firestore

import com.alpaca.hyperpong.util.enums.TipoCategoria
import com.alpaca.hyperpong.util.enums.TipoEvento
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale

data class Category(
    var date: Timestamp? = null,
    var id: String = "",
    var name: String = "",
    var max_participants: Int = 0,
    var price: String = "",
    var participants: List<Participant> = emptyList(),
) {
    val startHour: String
        get() {
            val sfd = SimpleDateFormat("HH:mm", Locale.getDefault())
            return date?.toDate()?.let { sfd.format(it) }.orEmpty()
        }

    val startDate: String
        get() {
            val sfd = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            return date?.toDate()?.let { sfd.format(it) }.orEmpty()
        }

    val tipoCategoria: TipoCategoria
        get() = when (id.toInt()) {
            1 -> TipoCategoria.RATING_A
            2 -> TipoCategoria.RATING_B
            3 -> TipoCategoria.INICIANTE
            4 -> TipoCategoria.FEMININO
            else -> TipoCategoria.DESCONHECIDO
        }
}
