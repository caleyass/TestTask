package com.obrio.test.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.obrio.test.data.local.entities.BalanceEntity
import com.obrio.test.data.local.entities.TransactionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BalanceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateBalance(balance: BalanceEntity)

    @Query("SELECT * FROM balance WHERE id = 1")
    suspend fun getBalance(): BalanceEntity

    @Query("SELECT * FROM balance WHERE id = 1")
    fun observeBalance(): Flow<BalanceEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: TransactionEntity)

    @Transaction
    suspend fun insertTransactionAndUpdateBalance(transaction: TransactionEntity, newBalance: BalanceEntity) {
        insertTransaction(transaction)
        updateBalance(newBalance)
    }
}