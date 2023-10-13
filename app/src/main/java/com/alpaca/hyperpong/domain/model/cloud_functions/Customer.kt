package com.alpaca.hyperpong.domain.model.cloud_functions

data class Customer(
    val myId: String? = null,
    val name: String? = null,
    val document: String? = null,
    val emails: String? = null,
    val phones: IntArray? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Customer

        return phones.contentEquals(other.phones)
    }

    override fun hashCode(): Int {
        return phones.contentHashCode()
    }
}
