package com.obrio.test.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.obrio.test.data.model.ResponseResult
import com.obrio.test.domain.usecase.GetAllTransactionUseCase
import com.obrio.test.presentation.mapper.toTransactionUiModel
import com.obrio.test.presentation.model.stateHolder.TransactionListStateHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionListViewModel @Inject constructor(
    private val getAllTransactionUseCase: GetAllTransactionUseCase,
) : ViewModel() {
    private val _transactionList = MutableStateFlow(TransactionListStateHolder(isLoading = true))
    val transactionList: StateFlow<TransactionListStateHolder> get() = _transactionList

    init {
        getAllTransaction()
    }

    private fun getAllTransaction(){
        viewModelScope.launch {
            getAllTransactionUseCase().collect{
                _transactionList.value = when(it){
                    is ResponseResult.Loading -> {
                        TransactionListStateHolder(isLoading = true)
                    }
                    is ResponseResult.Error -> {
                        TransactionListStateHolder(error = it.message)
                    }
                    is ResponseResult.Success -> {
                        TransactionListStateHolder(data = it.data.map { domainModel -> domainModel.toTransactionUiModel() })
                    }
                }
            }
        }
    }
}