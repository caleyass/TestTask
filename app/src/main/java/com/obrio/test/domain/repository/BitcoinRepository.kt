package com.obrio.test.domain.repository

import com.obrio.test.domain.model.BitcoinPrice
import com.obrio.test.data.model.ResponseResult

interface BitcoinRepository{
    suspend fun getBitcoinPrice() : ResponseResult<BitcoinPrice>
}