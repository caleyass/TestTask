package com.obrio.test.data.mapper

import com.obrio.test.data.network.model.BitcoinPriceResponseDto
import com.obrio.test.domain.model.BitcoinPrice

/**
 * Map
 * BitcoinPriceResponseDto (Network bitcoin price model) to
 * BitcoinPrice (Domain bitcoin price model)
 * */
fun BitcoinPriceResponseDto.toBitcoinPrice() : BitcoinPrice {
    return BitcoinPrice(usd = bpi.USD.rate_float)
}

/**
 * Map
 * Double (Database price model) to
 * BitcoinPrice (Domain bitcoin price model)
 * */
fun Double.toBitcoinPrice() : BitcoinPrice {
    return BitcoinPrice(usd = this)
}