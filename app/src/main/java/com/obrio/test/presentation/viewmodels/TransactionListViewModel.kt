package com.obrio.test.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.obrio.test.data.model.ResponseResult
import com.obrio.test.domain.model.Transaction
import com.obrio.test.domain.usecase.GetTransactionsUseCase
import com.obrio.test.presentation.mapper.toTransactionUiModel
import com.obrio.test.presentation.model.TransactionUiModel
import com.obrio.test.presentation.model.stateHolder.TransactionListStateHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionListViewModel @Inject constructor(
    getTransactionsUseCase: GetTransactionsUseCase,
) : ViewModel() {

    // PagingData Flow for transactions
    val transactionList: Flow<PagingData<Transaction>> = getTransactionsUseCase()
        .cachedIn(viewModelScope)

}