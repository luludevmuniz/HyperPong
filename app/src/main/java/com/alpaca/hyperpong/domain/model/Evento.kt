package com.alpaca.hyperpong.domain.model

import com.google.firebase.database.PropertyName

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
    @get:PropertyName("status") @set:PropertyName("status")
    var status: Int = 0,
    @get:PropertyName("time") @set:PropertyName("time")
    var hora: String = "",
    @get:PropertyName("type") @set:PropertyName("type")
    var tipo: String = "",
    @get:PropertyName("wallpaper") @set:PropertyName("wallpaper")
    var imagem: String = ""
)
