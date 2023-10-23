package com.alpaca.hyperpong.util.enums

enum class TipoCategoria {
    RATING_A {
        override fun toString(): String {
            return "Rating A"
        }
    },
    RATING_B {
        override fun toString(): String {
            return "Rating B"
        }
    },
    FEMININO {
        override fun toString(): String {
            return "Feminino"
        }
    },
    INICIANTE {
        override fun toString(): String {
            return "Iniciante"
        }
    },
    DESCONHECIDO;

    companion object {
        fun toEnum(categoryType: String): TipoCategoria {
            return TipoCategoria.entries.first { it.toString() == categoryType }
        }
    }
}