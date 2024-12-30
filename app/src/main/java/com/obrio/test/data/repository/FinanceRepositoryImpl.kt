package com.obrio.test.data.repository

import android.util.Log
import com.obrio.test.data.local.dao.BalanceDao
import com.obrio.test.data.local.dao.TransactionDao
import com.obrio.test.data.local.entities.BalanceEntity
import com.obrio.test.data.mapper.toBalance
import com.obrio.test.data.mapper.toBalanceEntity
import com.obrio.test.data.mapper.toTransactionEntity
import com.obrio.test.data.model.ResponseResult
import com.obrio.test.domain.model.Balance
import com.obrio.test.domain.model.Transaction
import com.obrio.test.domain.repository.FinanceRepository
import com.obrio.test.utils.AppLogger.Data.FINANCE_REPOSITORY_TAG
import javax.inject.Inject

class FinanceRepositoryImpl @Inject constructor(
    private val balanceDao: BalanceDao,
    private val transactionDao: TransactionDao
) : FinanceRepository {

    override suspend fun addTransaction(transaction: Transaction) {
        try {
            //count new balance (minus transaction amount)
            val currentBalance = balanceDao.getBalance().amount
            val newBalance = Balance(currentBalance - transaction.amount)
            balanceDao.insertTransactionAndUpdateBalance(transaction.toTransactionEntity(), newBalance.toBalanceEntity())
            Log.d(
                FINANCE_REPOSITORY_TAG,
                "addTransaction: Successfully updated balance: ${newBalance.amount}"
            )
            //ResponseResult.Success("Successfully updated balance")
        } catch (e: Exception) {
            Log.e(FINANCE_REPOSITORY_TAG, "Add transaction error: ${e.message}")
            //ResponseResult.Error(e.message.toString())
        }
    }

    override suspend fun addBalance(balance: Balance) {
        try {
            val newBalance = Balance(balanceDao.getBalance().amount + balance.amount)
            balanceDao.updateBalance(newBalance.toBalanceEntity())
            Log.d(
                FINANCE_REPOSITORY_TAG,
                "addBalance: Successfully updated balance: ${newBalance.amount}"
            )
        } catch (e: Exception) {
            Log.e(FINANCE_REPOSITORY_TAG, "updateBalance: ${e.message}")
        }
    }

    override suspend fun getAllTransactions(): ResponseResult<List<Transaction>> {
        Log.i(FINANCE_REPOSITORY_TAG, "getAllTransactions: Fetching local data...")
        return try {
            val allTransactions = transactionDao.getAllTransactions()
            Log.i(FINANCE_REPOSITORY_TAG, "getAllTransactions: ${allTransactions.size}")
            val transactions = allTransactions.map { entity ->
                Transaction(
                    id = entity.id,
                    amount = entity.amount,
                    category = entity.category,
                    timestamp = entity.timestamp
                )
            }
            ResponseResult.Success(transactions) // can be with size 0
        } catch (e: Exception) {
            ResponseResult.Error("Get transactions error: ${e.message}")
        }
    }

    override suspend fun getBalance(): ResponseResult<Balance> {
        Log.i(FINANCE_REPOSITORY_TAG, "getBalance: Fetching local data...")
        return try {
            var balanceEntity = balanceDao.getBalance()
            if (balanceEntity == null) {
                // Initialize balance if it doesn't exist
                balanceEntity = BalanceEntity() // Default amount is 0.0
                balanceDao.updateBalance(balanceEntity)
            }
            val balance = balanceEntity.toBalance()
            Log.i(FINANCE_REPOSITORY_TAG, "getBalance: ${balance.amount}")
            ResponseResult.Success(balance)// can be with size 0
        } catch (e: Exception) {
            Log.e(FINANCE_REPOSITORY_TAG, "getBalance: ${e.message}", )
            ResponseResult.Error("Get balance error: ${e.message}")
        }
    }

}