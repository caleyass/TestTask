package com.obrio.test.data.mapper

import com.obrio.test.data.network.model.BitcoinPriceResponseDto
import com.obrio.test.domain.model.BitcoinPrice

fun BitcoinPriceResponseDto.toBitcoinPrice() : BitcoinPrice {
    return BitcoinPrice(usd = bpi.USD.rate_float)
}