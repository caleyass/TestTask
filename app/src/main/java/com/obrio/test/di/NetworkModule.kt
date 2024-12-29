package com.obrio.test.di

import com.obrio.test.data.network.service.BitcoinApiService
import com.obrio.test.data.repository.BitcoinRepositoryImpl
import com.obrio.test.domain.repository.BitcoinRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://api.coindesk.com/v1/bpi/"

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): BitcoinApiService {
        return retrofit.create(BitcoinApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideBitcoinRepository(apiService: BitcoinApiService) : BitcoinRepository{
        return BitcoinRepositoryImpl(apiService = apiService)
    }
}
