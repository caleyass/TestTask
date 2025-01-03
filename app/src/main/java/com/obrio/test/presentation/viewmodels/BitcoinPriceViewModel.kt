package com.obrio.test.presentation.viewmodels

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.withResumed
import com.obrio.test.domain.usecase.GetBitcoinPriceUseCase
import com.obrio.test.presentation.model.stateHolder.BitcoinPriceStateHolder
import com.obrio.test.data.model.ResponseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BitcoinPriceViewModel @Inject constructor(
    private val getBitcoinPriceUseCase: GetBitcoinPriceUseCase
) : ViewModel() {

    private val _bitcoinPrice = MutableStateFlow(BitcoinPriceStateHolder(isLoading = true))
    val bitcoinPrice: StateFlow<BitcoinPriceStateHolder> get() = _bitcoinPrice

    fun getBitcoinPrice(){
        viewModelScope.launch {
            getBitcoinPriceUseCase().collect {
                _bitcoinPrice.value = when (it) {
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