package com.alpaca.hyperpong.presentation.screens.detalhes_evento

import androidx.lifecycle.ViewModel
import com.alpaca.hyperpong.domain.model.Evento
import com.alpaca.hyperpong.domain.model.cloud_functions.request_body.GetPaymentUrlRequestBody
import com.alpaca.hyperpong.domain.use_case.cloud_functions.get_payment_url.CloudFunctionsUseCase
import com.alpaca.hyperpong.util.Response
import com.alpaca.hyperpong.util.Response.Error
import com.alpaca.hyperpong.util.Response.Loading
import com.alpaca.hyperpong.util.Response.Success
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class DetalhesEventoViewModel @Inject constructor(
    private val useCases: CloudFunctionsUseCase
) : ViewModel() {
    private val _database = Firebase.database.reference
    private val _evento: MutableStateFlow<Evento?> =
        MutableStateFlow(null)
    val evento = _evento.asStateFlow()
    private val _response: MutableStateFlow<Response<String>> =
        MutableStateFlow(Response.Idle)
    val response = _response.asStateFlow()


    //TODO: Refatorar para UseCase após migrar para Firestore
    fun getEvento(id: String) {
        _database.child("events").child(id).get().addOnSuccessListener { retornoApi ->
            _evento.value = retornoApi.getValue(Evento::class.java)
        }.addOnFailureListener {
            //TODO...
        }
    }

    fun getPaymentUrl(
        body: GetPaymentUrlRequestBody
    ) {
        _response.value = Loading
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val apiResponse: retrofit2.Response<ResponseBody> = useCases.getPaymentUrlUseCase(body = body)
                if (apiResponse.isSuccessful) {
                    _response.value = Success(apiResponse.body()?.string().orEmpty())
                } else {
                    _response.value = Error(apiResponse.message())
                }
            } catch (e: HttpException) {
                e.printStackTrace()
            }
        }
    }

    fun getTomorrowDay(): String =
        LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
}