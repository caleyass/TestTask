package com.obrio.test.data.repository

import com.obrio.test.domain.model.BitcoinPrice
import com.obrio.test.domain.repository.BitcoinRepository
import com.obrio.test.utils.model.ResponseResult

class BitcoinRepositoryImpl : BitcoinRepository {
    override suspend fun fetchBitcoinData(): ResponseResult<BitcoinPrice> {
        TODO("Not yet implemented")
    }
}