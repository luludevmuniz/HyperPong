package com.alpaca.hyperpong.domain.model

import com.alpaca.hyperpong.util.StatusEvento
import com.alpaca.hyperpong.util.TipoEvento
import com.google.firebase.database.PropertyName
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class Evento(
    @get:PropertyName("address") @set:PropertyName("fieldName")
    var endereco: String = "",
    @get:PropertyName("categories") @set:PropertyName("categories")
    var categorias: List<Categoria> = emptyList(),
    @get:PropertyName("date") @set:PropertyName("date")
    var dataInicio: String = "",
    @get:PropertyName("description") @set:PropertyName("description")
    var descricao: String = "",
    @get:PropertyName("end_date") @set:PropertyName("end_date")
    var dataFim: String = "",
    @get:PropertyName("id") @set:PropertyName("id")
    var id: String = "",
    @get:PropertyName("name") @set:PropertyName("name")
    var nome: String = "",
    val status: Int? = null,
    @get:PropertyName("time") @set:PropertyName("time")
    var hora: String = "",
    @get:PropertyName("type") @set:PropertyName("type")
    var tipo: String = "",
    @get:PropertyName("wallpaper") @set:PropertyName("wallpaper")
    var imagem: String = ""
) {
    val statusEvento: StatusEvento
        get() = when (status) {
            0 -> StatusEvento.FUTURO
            1 -> StatusEvento.ABERTO
            2 -> StatusEvento.FINALIZADO
            else -> StatusEvento.DESCONHECIDO
        }

    val tipoEvento: TipoEvento
        get() = when (tipo.toIntOrNull()) {
            0 -> TipoEvento.COPA_HYPER
            1 -> TipoEvento.TORNEIO_INERNO
            2 -> TipoEvento.RACHAO
            3 -> TipoEvento.BATE_BOLA
            else -> TipoEvento.BATE_BOLA
        }

    val dataInicioFormatada: String
        get() {
            val dataLocal = LocalDate.parse(dataInicio, DateTimeFormatter.ISO_DATE)
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            return dataLocal.format(formatter)
        }

    fun isConcluido(): Boolean {
        val dataAPI = LocalDate.parse(this.dataInicio, DateTimeFormatter.ISO_DATE)
        return dataAPI.isBefore(LocalDate.now()) || dataAPI.isEqual(LocalDate.now())
    }

    fun isFuturo(): Boolean =
        LocalDate.parse(this.dataInicio, DateTimeFormatter.ISO_DATE).isAfter(LocalDate.now())
}
