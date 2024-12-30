package com.obrio.test.domain.repository

import com.obrio.test.data.model.ResponseResult
import com.obrio.test.domain.model.Balance
import com.obrio.test.domain.model.Transaction

interface FinanceRepository {
    suspend fun addTransaction(transaction: Transaction)

    suspend fun getAllTransactions(): ResponseResult<List<Transaction>>

    suspend fun addBalance(balance: Balance)

    suspend fun getBalance(): ResponseResult<Balance>
}