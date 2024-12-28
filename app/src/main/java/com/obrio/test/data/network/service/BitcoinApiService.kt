package com.obrio.test.data.network.service

import com.obrio.test.data.network.model.BitcoinPriceResponseDto
import retrofit2.Response
import retrofit2.http.GET

interface BitcoinApiService {
    @GET("currentprice.json")
    suspend fun getBitcoinPrice() : Response<BitcoinPriceResponseDto>
}