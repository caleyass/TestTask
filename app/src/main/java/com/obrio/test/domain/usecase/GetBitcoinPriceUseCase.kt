package com.obrio.test.domain.usecase

import com.obrio.test.domain.model.BitcoinPrice
import com.obrio.test.domain.repository.BitcoinRepository
import com.obrio.test.utils.model.ResponseResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetBitcoinPriceUseCase @Inject constructor(
    private val bitcoinRepository: BitcoinRepository
)  {

    operator fun invoke() : Flow<ResponseResult<BitcoinPrice>> =
        flow {
            emit(ResponseResult.Loading)
            emit(bitcoinRepository.getBitcoinPrice())
        }
            .catch { e ->
                emit(ResponseResult.Error(message = e.message ?: "Unknown error"))
            }
            .flowOn(Dispatchers.IO)
}