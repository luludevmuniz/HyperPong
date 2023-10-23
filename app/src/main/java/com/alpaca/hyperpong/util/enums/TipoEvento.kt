package com.alpaca.hyperpong.util.enums

enum class TipoEvento {
    COPA_HYPER {
        override fun toString(): String {
            return "Copa Hyper"
        }
    },
    RACHAO {
        override fun toString(): String {
            return "Rachão"
        }
    },
    TORNEIO_INERNO {
        override fun toString(): String {
            return "Torneio Interno"
        }
    },
    BATE_BOLA {
        override fun toString(): String {
            return "Bate Bola"
        }
    };

    companion object {
         fun toEnum(eventType: String): TipoEvento {
            return entries.first { it.toString() == eventType }
        }
    }
}