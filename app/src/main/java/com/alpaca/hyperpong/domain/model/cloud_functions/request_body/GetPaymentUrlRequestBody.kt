package com.alpaca.hyperpong.domain.model.cloud_functions.request_body

import com.alpaca.hyperpong.domain.model.cloud_functions.Customer

data class GetPaymentUrlRequestBody(
    val myId: String? = null,
    val value: Long,
    val payday: String,
    val mainPaymentMethodId: String,
    val Customer: Customer
)
