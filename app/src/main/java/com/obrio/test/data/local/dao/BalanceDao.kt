package com.obrio.test.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.obrio.test.data.local.entities.BalanceEntity

@Dao
interface BalanceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateBalance(balance: BalanceEntity)

    @Query("SELECT * FROM balance WHERE id = 1")
    suspend fun getBalance(): BalanceEntity?
}