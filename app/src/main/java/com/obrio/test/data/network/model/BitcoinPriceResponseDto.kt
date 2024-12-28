package com.obrio.test.data.network.model

data class BitcoinPriceResponseDto(
    val bpi: BpiDto
)

data class BpiDto(
    val USD: CurrencyDto
)

data class CurrencyDto(
    val rate_float: Double
)