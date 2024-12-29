package com.obrio.test.presentation.model

import com.obrio.test.domain.model.BitcoinPrice
import com.obrio.test.utils.model.StateHolder

data class BitcoinPriceStateHolder(
    override var isLoading: Boolean = false,
    override var data: BitcoinPrice? = null,
    override var error: String = ""
) : StateHolder<BitcoinPrice>