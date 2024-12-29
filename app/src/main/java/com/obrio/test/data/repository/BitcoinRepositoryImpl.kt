package com.obrio.test.data.repository

import android.util.Log
import com.obrio.test.data.local.dao.BitcoinDataDao
import com.obrio.test.data.local.entities.BitcoinDataEntity
import com.obrio.test.data.mapper.toBitcoinPrice
import com.obrio.test.data.network.service.BitcoinApiService
import com.obrio.test.domain.model.BitcoinPrice
import com.obrio.test.domain.repository.BitcoinRepository
import com.obrio.test.data.model.ResponseResult
import com.obrio.test.data.utils.TimeUtils
import com.obrio.test.utils.AppLogger.Data.BITCOIN_REPOSITORY_TAG
import javax.inject.Inject

/**
 * Implementation of the BitcoinRepository interface.
 * This class handles fetching Bitcoin prices from the network or local storage based on certain conditions.
 *
 * @property bitcoinApiService Service responsible for fetching Bitcoin prices from the network.
 * @property bitcoinDao DAO that holds BitcoinDataEntity
 */
class BitcoinRepositoryImpl @Inject constructor(
    private val bitcoinApiService: BitcoinApiService,
    private val bitcoinDao : BitcoinDataDao
) : BitcoinRepository {

    /**
     * Retrieves the current Bitcoin price.
     * If a network fetch is required, it fetches from the API; otherwise, it fetches from local storage.
     *
     * @return A ResponseResult containing either the Bitcoin price (on success) or an error message.
     */
    override suspend fun getBitcoinPrice(): ResponseResult<BitcoinPrice> =
        if (fetchFromNetwork()) {
            getBitcoinPriceNetwork()
        } else {
            getBitcoinPriceLocal()
        }

    /**
     * Fetches the Bitcoin price from DB.
     *
     * - Returns a successful result if the price is found in local storage.
     * - Returns an error result if the price is missing or if an exception occurs.
     *
     * @return A ResponseResult containing either the Bitcoin price or an error message.
     */
    private suspend fun getBitcoinPriceLocal(): ResponseResult<BitcoinPrice> {
        Log.i(BITCOIN_REPOSITORY_TAG, "getBitcoinPriceLocal: Fetching local data...")
        return try {
            val lastBitcoinPrice = bitcoinDao.getLastBitcoinPrice()
            lastBitcoinPrice?.let {
                Log.i(BITCOIN_REPOSITORY_TAG, "getBitcoinPriceLocal: $lastBitcoinPrice")
                ResponseResult.Success(lastBitcoinPrice.toBitcoinPrice())
            } ?: ResponseResult.Error("Empty bitcoin price response")
        } catch (e: Exception) {
            ResponseResult.Error("Data store error: ${e.message}")
        }
    }

    /**
     * Fetches the Bitcoin price from the network (API).
     *
     * - Returns a successful result if the API call is successful and the response body is not empty.
     * - Saves the fetched price and timestamp in DB for future use.
     * - Returns an error result if the API call fails or if an exception occurs.
     *
     * @return A ResponseResult containing either the Bitcoin price or an error message.
     */
    private suspend fun getBitcoinPriceNetwork(): ResponseResult<BitcoinPrice> {
        Log.i(BITCOIN_REPOSITORY_TAG, "getBitcoinPriceNetwork: Fetching remote data...")
        return try {
            val response = bitcoinApiService.getBitcoinPrice()
            if (response.isSuccessful) {
                // Map the response body to the domain model and return it as Success
                response.body()?.let {
                    val bitcoinPrice = it.toBitcoinPrice()
                    Log.i(BITCOIN_REPOSITORY_TAG, "getBitcoinPrice: ${bitcoinPrice.usd}")
                    saveLastFetchTimeAndBitcoinPrice(timeStamp = System.currentTimeMillis(), bitcoinPrice = bitcoinPrice.usd)
                    ResponseResult.Success(bitcoinPrice)
                } ?: ResponseResult.Error("Empty network response body")
            } else {
                // If not successful, return an error with a message from the response
                ResponseResult.Error("Error: ${response.message()}")
            }
        } catch (e: Exception) {
            // Catch any errors and return an error result
            ResponseResult.Error("Network error: ${e.message}")
        }
    }

    /**
     * Saves the last fetch time and Bitcoin price into local storage.
     *
     * - Catches and logs any exceptions that occur during the saving process.
     *
     * @param timeStamp The timestamp of the last fetch.
     * @param bitcoinPrice The fetched Bitcoin price.
     */
    private suspend fun saveLastFetchTimeAndBitcoinPrice(timeStamp: Long, bitcoinPrice: Double){
        try {
            bitcoinDao.saveBitcoinData(BitcoinDataEntity(1, timeStamp, bitcoinPrice))
            Log.d(BITCOIN_REPOSITORY_TAG, "saveLastFetchTimeAndBitcoinPrice: Successfully saved ($timeStamp, $bitcoinPrice)")
        } catch (
            e:Exception
        ){
            Log.e(BITCOIN_REPOSITORY_TAG, "saveLastFetchTimeAndBitcoinPrice: ${e.message}")
        }
    }

    /**
     * Determines whether a network fetch is required based on the last fetch time.
     *
     * - Fetches the last fetch time from DB.
     * - Returns `true` if no fetch has been made before (lastFetchTime is null)
     *   or if more than one hour has passed since the last fetch.
     * - Returns `false` otherwise.
     *
     * @return `true` if a network fetch is needed, `false` otherwise.
     */
    private suspend fun fetchFromNetwork(): Boolean {
        val lastFetchTime = bitcoinDao.getLastFetchTime()
        return lastFetchTime == null || TimeUtils.isMoreThanAnHourAgo(lastFetchTime)
    }
}