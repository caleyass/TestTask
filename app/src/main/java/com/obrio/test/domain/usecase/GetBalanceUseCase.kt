package com.obrio.test.domain.usecase

import com.obrio.test.data.model.ResponseResult
import com.obrio.test.domain.model.Balance
import com.obrio.test.domain.repository.FinanceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetBalanceUseCase @Inject constructor(
    private val financeRepository: FinanceRepository
) {
    operator fun invoke() : Flow<ResponseResult<Balance>> =
        flow {
            emit(ResponseResult.Loading)
            financeRepository.observeBalance()
                .collect { balance ->
                    emit(ResponseResult.Success(balance))
                }
        }.catch { e ->
            emit(ResponseResult.Error(message = e.message ?: "Unknown error"))
        }
}