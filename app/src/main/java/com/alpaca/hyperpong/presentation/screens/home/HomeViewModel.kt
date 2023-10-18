package com.alpaca.hyperpong.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.alpaca.hyperpong.domain.use_case.firestore.FirestoreUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    firestoreUseCases: FirestoreUseCases
) : ViewModel() {
    val eventos = firestoreUseCases.getEventosUseCase().cachedIn(viewModelScope)
}
