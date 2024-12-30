package com.obrio.test.data.local.dao

import android.util.Log
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.obrio.test.data.local.entities.BalanceEntity
import com.obrio.test.data.local.entities.TransactionEntity

@Dao
interface BalanceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateBalance(balance: BalanceEntity)

    @Query("SELECT * FROM balance WHERE id = 1")
    suspend fun getBalance(): BalanceEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: TransactionEntity)

    @Transaction
    suspend fun insertTransactionAndUpdateBalance(transaction: TransactionEntity, newBalance: BalanceEntity) {
        insertTransaction(transaction)
        updateBalance(newBalance)
    }
}