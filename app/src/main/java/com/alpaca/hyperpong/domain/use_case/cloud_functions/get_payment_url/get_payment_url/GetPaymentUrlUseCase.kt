package com.alpaca.hyperpong.domain.use_case.cloud_functions.get_payment_url.get_payment_url

import com.alpaca.hyperpong.domain.model.cloud_functions.request_body.GetPaymentUrlRequestBody
import com.alpaca.hyperpong.domain.repository.CloudFunctionsRepository
import okhttp3.ResponseBody
import retrofit2.Response

class GetPaymentUrlUseCase(
    private val repository: CloudFunctionsRepository
) {
    suspend operator fun invoke(body: GetPaymentUrlRequestBody): Response<ResponseBody> =
        repository.getPaymentUrl(body = body)
}