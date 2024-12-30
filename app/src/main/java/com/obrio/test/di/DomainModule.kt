package com.obrio.test.di

import com.obrio.test.data.local.dao.BalanceDao
import com.obrio.test.data.local.dao.BitcoinDataDao
import com.obrio.test.data.local.dao.TransactionDao
import com.obrio.test.data.network.service.BitcoinApiService
import com.obrio.test.data.repository.BitcoinRepositoryImpl
import com.obrio.test.data.repository.FinanceRepositoryImpl
import com.obrio.test.domain.repository.BitcoinRepository
import com.obrio.test.domain.repository.FinanceRepository
import com.obrio.test.domain.usecase.GetBitcoinPriceUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @Singleton
    fun provideBitcoinRepository(
        apiService: BitcoinApiService,
        bitcoinDataDao: BitcoinDataDao
    ): BitcoinRepository {
        return BitcoinRepositoryImpl(
            bitcoinApiService = apiService,
            bitcoinDao = bitcoinDataDao
        )
    }

    @Provides
    @Singleton
    fun provideFinanceRepository(
        balanceDao: BalanceDao,
        transactionDao: TransactionDao
    ) : FinanceRepository {
        return FinanceRepositoryImpl(
            balanceDao = balanceDao,
            transactionDao = transactionDao
        )
    }

}