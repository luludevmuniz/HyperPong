package com.alpaca.hyperpong.data.remote

import com.alpaca.hyperpong.domain.model.cloud_functions.request_body.GetPaymentUrlRequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface CloudFunctionsApi {
    @POST("/getPaymentUrl")
    suspend fun getPaymentUrl(
        @Body paymentsBody: GetPaymentUrlRequestBody
    ): Response<ResponseBody>
}