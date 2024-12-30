package com.obrio.test.domain.repository

import com.obrio.test.data.model.ResponseResult
import com.obrio.test.domain.model.Balance
import com.obrio.test.domain.model.Transaction
import kotlinx.coroutines.flow.Flow

interface FinanceRepository {
    suspend fun addTransaction(transaction: Transaction) : ResponseResult<String>

    suspend fun getAllTransactions(): Flow<ResponseResult<List<Transaction>>>

    suspend fun addBalance(balance: Balance)

    suspend fun getBalance(): ResponseResult<Balance>

    suspend fun observeBalance() : Flow<Balance>
}