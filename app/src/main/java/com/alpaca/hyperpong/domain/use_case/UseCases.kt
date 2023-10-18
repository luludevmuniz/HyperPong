package com.alpaca.hyperpong.domain.use_case

import com.alpaca.hyperpong.domain.use_case.authentication.AuthUseCases
import com.alpaca.hyperpong.domain.use_case.cloud_functions.get_payment_url.CloudFunctionsUseCases
import com.alpaca.hyperpong.domain.use_case.firestore.FirestoreUseCases

data class UseCases(
    val firestoreUseCase: FirestoreUseCases,
    val cloudFunctionsUseCases: CloudFunctionsUseCases,
    val authUseCases: AuthUseCases
)