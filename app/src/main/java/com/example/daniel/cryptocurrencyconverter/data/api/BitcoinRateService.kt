package com.example.daniel.cryptocurrencyconverter.data.api

import com.example.daniel.cryptocurrencyconverter.data.BitcoinExchangeRateRaw
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET

interface BitcoinRateService {
    @GET("v1/bpi/currentprice.json")
    fun getBitcoinRate(): Observable<BitcoinExchangeRateRaw>
}