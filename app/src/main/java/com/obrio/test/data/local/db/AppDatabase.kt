package com.obrio.test.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.obrio.test.data.local.dao.BitcoinDataDao
import com.obrio.test.data.local.entities.BitcoinDataEntity

@Database(entities = [BitcoinDataEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bitcoinDataDao(): BitcoinDataDao
}