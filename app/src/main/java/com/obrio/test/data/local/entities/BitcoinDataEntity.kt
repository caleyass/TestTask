package com.obrio.test.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.obrio.test.utils.Constants

@Entity(tableName = Constants.BITCOIN_DATA_ENTITY_NAME)
data class BitcoinDataEntity(
    @PrimaryKey val id: Int = 1,
    val lastFetchTime: Long,
    val lastBitcoinPrice: Double
)