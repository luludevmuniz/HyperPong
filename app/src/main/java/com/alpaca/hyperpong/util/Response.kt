package com.alpaca.hyperpong.util

sealed interface Response<out T> {
    data object Idle : Response<Nothing>
    data object Loading : Response<Nothing>
    data class Success<T>(val data: T) : Response<T>
    data class Error(val error: String) : Response<Nothing>
}

const val ERROR_MESSAGE = "Não foi possível concluir a ação."