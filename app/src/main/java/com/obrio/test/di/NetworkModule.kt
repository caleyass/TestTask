package com.obrio.test.di

import com.obrio.test.data.local.dao.BitcoinDataDao
import com.obrio.test.data.network.service.BitcoinApiService
import com.obrio.test.data.repository.BitcoinRepositoryImpl
import com.obrio.test.domain.repository.BitcoinRepository
import com.obrio.test.utils.Constants.BASE_URL
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
}
