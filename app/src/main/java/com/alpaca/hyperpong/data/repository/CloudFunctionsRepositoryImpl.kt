package com.alpaca.hyperpong.data.repository

import com.alpaca.hyperpong.data.remote.CloudFunctionsApi
import com.alpaca.hyperpong.domain.model.cloud_functions.request_body.GetPaymentUrlRequestBody
import com.alpaca.hyperpong.domain.repository.CloudFunctionsRepository
import okhttp3.ResponseBody
import retrofit2.Response

class CloudFunctionsRepositoryImpl(
    private val cloudFunctionsApi: CloudFunctionsApi
) : CloudFunctionsRepository {
    override suspend fun getPaymentUrl(body: GetPaymentUrlRequestBody): Response<ResponseBody> =
        cloudFunctionsApi.getPaymentUrl(paymentsBody = body)
}