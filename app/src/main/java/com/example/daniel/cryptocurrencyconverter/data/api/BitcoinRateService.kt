package com.example.daniel.cryptocurrencyconverter.data

import io.reactivex.Single
import retrofit2.http.GET

/**
 * Created by Daniel on 3/20/2018.
 */
interface BitcoinRateService {
    @GET("v1/bpi/currentprice.json")
    fun getCreditDrafts(): Single<BitcoinExchangeRateRaw>
}