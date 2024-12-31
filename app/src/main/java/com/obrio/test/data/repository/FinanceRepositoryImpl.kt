package com.obrio.test.data.repository

import android.util.Log
import androidx.paging.PagingSource
import com.obrio.test.data.local.dao.BalanceDao
import com.obrio.test.data.local.dao.TransactionDao
import com.obrio.test.data.local.entities.TransactionEntity
import com.obrio.test.data.mapper.toBalance
import com.obrio.test.data.mapper.toBalanceEntity
import com.obrio.test.data.mapper.toTransactionEntity
import com.obrio.test.data.model.ResponseResult
import com.obrio.test.domain.model.Balance
import com.obrio.test.domain.model.Transaction
import com.obrio.test.domain.repository.FinanceRepository
import com.obrio.test.utils.AppLogger.Data.FINANCE_REPOSITORY_TAG
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FinanceRepositoryImpl @Inject constructor(
    private val balanceDao: BalanceDao,
    private val transactionDao: TransactionDao
) : FinanceRepository {

    override suspend fun addTransaction(transaction: Transaction): ResponseResult<String> {
        if (transaction.category != null && balanceDao.getBalance().amount < transaction.amount) {
            return ResponseResult.Error("Balance is not enough")
        } else {
            try {
                val currentBalance = balanceDao.getBalance().amount

                val newBalance = if (transaction.category == null) {
                    currentBalance + transaction.amount
                } else {
                    currentBalance - transaction.amount
                }
                balanceDao.insertTransactionAndUpdateBalance(
                    transaction.toTransactionEntity(),
                    Balance(newBalance).toBalanceEntity()
                )
                Log.d(
                    FINANCE_REPOSITORY_TAG,
                    "addTransaction: Successfully updated balance: ${newBalance}"
                )
                return ResponseResult.Success("Transaction is successful")
            } catch (e: Exception) {
                Log.e(FINANCE_REPOSITORY_TAG, "Add transaction error: ${e.message}")
                return ResponseResult.Error("Error occurred during transaction")
            }
        }
    }

    override fun getTransactionsPaged(): PagingSource<Int, TransactionEntity> {
        Log.i(FINANCE_REPOSITORY_TAG, "getTransactions: Fetching local data...")
        return transactionDao.getTransactionsPaged()

    }

    override suspend fun getBalance(): ResponseResult<Balance> {
        Log.i(FINANCE_REPOSITORY_TAG, "getBalance: Fetching local data...")
        return try {
            val balance = balanceDao.getBalance().toBalance()
            Log.i(FINANCE_REPOSITORY_TAG, "getBalance: ${balance.amount}")
            ResponseResult.Success(balance)// can be with size 0
        } catch (e: Exception) {
            Log.e(FINANCE_REPOSITORY_TAG, "getBalance: ${e.message}")
            ResponseResult.Error("Get balance error: ${e.message}")
        }
    }

    override suspend fun observeBalance(): Flow<Balance> {
        return balanceDao.observeBalance().map { it.toBalance() }
    }

}