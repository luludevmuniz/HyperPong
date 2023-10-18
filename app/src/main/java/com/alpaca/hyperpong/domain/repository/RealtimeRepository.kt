package com.alpaca.hyperpong.domain.repository

import androidx.paging.PagingData
import com.alpaca.hyperpong.domain.model.Event
import kotlinx.coroutines.flow.Flow

typealias RetornoEventos = Flow<PagingData<Event>>

