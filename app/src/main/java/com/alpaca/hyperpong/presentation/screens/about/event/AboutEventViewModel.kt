package com.alpaca.hyperpong.presentation.screens.about.event

import androidx.lifecycle.ViewModel
import com.alpaca.hyperpong.domain.model.Event
import com.alpaca.hyperpong.domain.model.cloud_functions.request_body.GetPaymentUrlRequestBody
import com.alpaca.hyperpong.domain.use_case.UseCases
import com.alpaca.hyperpong.util.Response
import com.alpaca.hyperpong.util.Response.Error
import com.alpaca.hyperpong.util.Response.Idle
import com.alpaca.hyperpong.util.Response.Loading
import com.alpaca.hyperpong.util.Response.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class AboutEventViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel() {
    private val _event: MutableStateFlow<Response<Event?>> =
        MutableStateFlow(Idle)
    val event = _event.asStateFlow()
    private val _paymentUrl: MutableStateFlow<Response<String>> =
        MutableStateFlow(Idle)
    val paymentUrl = _paymentUrl.asStateFlow()

    fun getEvento(id: String) {
        CoroutineScope(Dispatchers.IO).launch {
            useCases.firestoreUseCase.getEventUseCase(id = id).collectLatest { eventResponse ->
                _event.value = eventResponse
            }
        }
    }

    fun getPaymentUrl(
        body: GetPaymentUrlRequestBody
    ) {
        _paymentUrl.value = Loading
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val apiResponse: retrofit2.Response<ResponseBody> =
                    useCases.cloudFunctionsUseCases.getPaymentUrlUseCase(body = body)
                if (apiResponse.isSuccessful) {
                    _paymentUrl.value = Success(apiResponse.body()?.string().orEmpty())
                } else {
                    _paymentUrl.value = Error(apiResponse.message())
                }
            } catch (e: HttpException) {
                _paymentUrl.value = Error(e.message())
            }
        }
    }

    fun getTomorrowDay(): String =
        LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
}