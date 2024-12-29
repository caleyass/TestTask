package com.obrio.test.di

import com.obrio.test.data.network.service.BitcoinApiService
import com.obrio.test.domain.repository.BitcoinRepository
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
    fun provideGetBitcoinUseCase(bitcoinRepository: BitcoinRepository) : GetBitcoinPriceUseCase{
        return GetBitcoinPriceUseCase(bitcoinRepository = bitcoinRepository)
    }
}