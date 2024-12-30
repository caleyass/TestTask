package com.obrio.test.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.obrio.test.data.local.converter.TransactionCategoryConverter
import com.obrio.test.data.local.dao.BalanceDao
import com.obrio.test.data.local.dao.BitcoinDataDao
import com.obrio.test.data.local.dao.TransactionDao
import com.obrio.test.data.local.entities.BalanceEntity
import com.obrio.test.data.local.entities.BitcoinDataEntity
import com.obrio.test.data.local.entities.TransactionEntity

@Database(entities = [BitcoinDataEntity::class, TransactionEntity::class, BalanceEntity::class], version = 3, exportSchema = false)
@TypeConverters(TransactionCategoryConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bitcoinDataDao(): BitcoinDataDao
    abstract fun transactionDao(): TransactionDao
    abstract fun balanceDao(): BalanceDao
}