package com.obrio.test.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.obrio.test.domain.usecase.GetBitcoinPriceUseCase
import com.obrio.test.presentation.model.BitcoinPriceStateHolder
import com.obrio.test.utils.model.ResponseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionListViewModel @Inject constructor(
    private val getBitcoinPriceUseCase: GetBitcoinPriceUseCase
) : ViewModel() {

    private val _bitcoinPrice = MutableStateFlow(BitcoinPriceStateHolder(isLoading = true))
    val bitcoinPrice: StateFlow<BitcoinPriceStateHolder> get() = _bitcoinPrice

    init {
        getBitcoinPrice()
    }

    private fun getBitcoinPrice(){
        viewModelScope.launch {
            getBitcoinPriceUseCase().collect {
                _bitcoinPrice.value = when (it){
                    is ResponseResult.Loading -> {
                        BitcoinPriceStateHolder(isLoading = true)
                    }
                    is ResponseResult.Error -> {
                        BitcoinPriceStateHolder(error = it.message)
                    }
                    is ResponseResult.Success -> {
                        BitcoinPriceStateHolder(data = it.data)
                    }
                }
            }
        }
    }
}