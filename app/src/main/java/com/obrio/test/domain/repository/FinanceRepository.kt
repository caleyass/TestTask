package com.obrio.test.domain.repository

import androidx.paging.PagingSource
import com.obrio.test.data.local.entities.TransactionEntity
import com.obrio.test.data.model.ResponseResult
import com.obrio.test.domain.model.Balance
import com.obrio.test.domain.model.Transaction
import kotlinx.coroutines.flow.Flow

interface FinanceRepository {
    suspend fun addTransaction(transaction: Transaction) : ResponseResult<String>

    fun getTransactionsPaged(): PagingSource<Int, TransactionEntity>

    suspend fun getBalance(): ResponseResult<Balance>

    suspend fun observeBalance() : Flow<Balance>
}