package com.obrio.test.di

import android.content.Context
import androidx.room.Room
import com.obrio.test.data.local.dao.BalanceDao
import com.obrio.test.data.local.dao.BitcoinDataDao
import com.obrio.test.data.local.dao.TransactionDao
import com.obrio.test.data.local.db.AppDatabase
import com.obrio.test.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            Constants.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideBitcoinDataDao(database: AppDatabase): BitcoinDataDao {
        return database.bitcoinDataDao()
    }

    @Provides
    fun provideBalanceDao(database: AppDatabase): BalanceDao {
        return database.balanceDao()
    }

    @Provides
    fun provideTransactionDao(database: AppDatabase): TransactionDao {
        return database.transactionDao()
    }
}