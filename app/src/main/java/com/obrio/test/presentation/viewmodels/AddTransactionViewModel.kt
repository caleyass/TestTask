package com.obrio.test.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.obrio.test.data.model.ResponseResult
import com.obrio.test.domain.model.Transaction
import com.obrio.test.domain.usecase.AddTransactionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTransactionViewModel @Inject constructor(
    private val addTransactionUseCase : AddTransactionUseCase
) : ViewModel() {
    private val _transactionResponse = MutableStateFlow<String?>(null)
    val transactionResponse: StateFlow<String?> get() = _transactionResponse

    fun addTransaction(transaction: Transaction){
        if(transaction.amount > 0) {
            viewModelScope.launch {
                val response = addTransactionUseCase(transaction)
                when (response) {
                    is ResponseResult.Error -> {
                        _transactionResponse.value = response.message
                    }

                    ResponseResult.Loading -> {}
                    is ResponseResult.Success -> {
                        _transactionResponse.value = response.data
                    }
                }
            }
        }
    }
}