package com.obrio.test.domain.repository

import com.obrio.test.domain.model.BitcoinPrice
import com.obrio.test.utils.model.ResponseResult

interface BitcoinRepository{
    suspend fun fetchBitcoinData() : ResponseResult<BitcoinPrice>
}