package com.obrio.test.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.obrio.test.data.model.ResponseResult
import com.obrio.test.domain.model.Balance
import com.obrio.test.domain.usecase.AddBalanceUseCase
import com.obrio.test.domain.usecase.GetBalanceUseCase
import com.obrio.test.presentation.model.stateHolder.BalanceStateHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BalanceViewModel @Inject constructor(
    private val getBalanceUseCase: GetBalanceUseCase,
    private val addBalanceUseCase: AddBalanceUseCase
) : ViewModel() {
    private val _balance = MutableStateFlow(BalanceStateHolder(isLoading = true))
    val balance: StateFlow<BalanceStateHolder> get() = _balance

    init {
        getBalance()
    }

    fun getBalance(){
        viewModelScope.launch {
             getBalanceUseCase().collect{
                 _balance.value = when (it){
                    is ResponseResult.Loading -> {
                        BalanceStateHolder(isLoading = true)
                    }
                    is ResponseResult.Error -> {
                        BalanceStateHolder(error = it.message)
                    }
                    is ResponseResult.Success -> {
                        BalanceStateHolder(data = it.data)
                    }
                }
            }
        }
    }

    fun addBalance(amount : Double){
        viewModelScope.launch {
            addBalanceUseCase(Balance(amount))
        }
    }
}