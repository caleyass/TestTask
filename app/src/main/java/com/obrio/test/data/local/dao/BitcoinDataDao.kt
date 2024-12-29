package com.obrio.test.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.obrio.test.data.local.entities.BitcoinDataEntity

@Dao
interface BitcoinDataDao {
    @Query("SELECT lastFetchTime FROM bitcoin_data WHERE id = 1")
    suspend fun getLastFetchTime(): Long?

    @Query("SELECT lastBitcoinPrice FROM bitcoin_data WHERE id = 1")
    suspend fun getLastBitcoinPrice(): Double?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveBitcoinData(data: BitcoinDataEntity)
}