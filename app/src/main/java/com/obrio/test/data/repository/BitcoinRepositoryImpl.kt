package com.obrio.test.data.repository

import com.obrio.test.data.mapper.toBitcoinPrice
import com.obrio.test.data.network.service.BitcoinApiService
import com.obrio.test.domain.model.BitcoinPrice
import com.obrio.test.domain.repository.BitcoinRepository
import com.obrio.test.utils.model.ResponseResult
import javax.inject.Inject

class BitcoinRepositoryImpl @Inject constructor(
    private val apiService: BitcoinApiService
) : BitcoinRepository {
    override suspend fun getBitcoinPrice(): ResponseResult<BitcoinPrice> {
        return try {
            val response = apiService.getBitcoinPrice()

            if (response.isSuccessful) {
                // Map the response body to the domain model and return it as Success
                response.body()?.let {
                    ResponseResult.Success(it.toBitcoinPrice())
                } ?: ResponseResult.Error("Empty response body")
            } else {
                // If not successful, return an error with a message from the response
                ResponseResult.Error("Error: ${response.message()}")
            }
        } catch (e: Exception) {
            // Catch any errors and return an error result
            ResponseResult.Error("Network error: ${e.message}")
        }
    }
}