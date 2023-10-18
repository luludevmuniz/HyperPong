package com.alpaca.hyperpong.domain.model

import com.alpaca.hyperpong.util.StatusEvento
import com.alpaca.hyperpong.util.TipoEvento
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date
import java.util.HashMap
import java.util.Locale

data class Event(
    var id: String = "",
    var categories: List<HashMap<String, Any>> = emptyList(),
    var description: String = "",
    var end_date: Timestamp? = null,
    var image: String = "",
    var start_date: Timestamp? = null,
    val status: Int? = null,
    var title: String = "",
    var type: Long = 0
) {
    val statusEvento: StatusEvento
        get() = when (status) {
            0 -> StatusEvento.FUTURO
            1 -> StatusEvento.ABERTO
            2 -> StatusEvento.FINALIZADO
            else -> StatusEvento.DESCONHECIDO
        }

    val tipoEvento: TipoEvento
        get() = when (type.toInt()) {
            0 -> TipoEvento.COPA_HYPER
            1 -> TipoEvento.TORNEIO_INERNO
            2 -> TipoEvento.RACHAO
            3 -> TipoEvento.BATE_BOLA
            else -> TipoEvento.BATE_BOLA
        }

    val dataInicioFormatada: String
        get() {
            val sfd = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            return start_date?.toDate()?.let { sfd.format(it) }.orEmpty()
        }

    fun isConcluido(): Boolean = this.start_date?.toDate()
        ?.before(
            Date.from(
                LocalDate
                    .now()
                    .atStartOfDay(
                        ZoneId.systemDefault()
                    )
                    .toInstant()
            )
        ) ?: false

    fun isFuturo(): Boolean =
        this.end_date?.toDate()
            ?.after(
                Date.from(
                    LocalDate
                        .now()
                        .atStartOfDay(
                            ZoneId.systemDefault()
                        )
                        .toInstant()
                )
            ) ?: false
}
