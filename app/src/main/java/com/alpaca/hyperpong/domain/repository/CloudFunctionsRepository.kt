package com.alpaca.hyperpong.domain.repository

import com.alpaca.hyperpong.domain.model.cloud_functions.request_body.GetPaymentUrlRequestBody
import okhttp3.ResponseBody
import retrofit2.Response

interface CloudFunctionsRepository {
    suspend fun getPaymentUrl(body: GetPaymentUrlRequestBody): Response<ResponseBody>
}