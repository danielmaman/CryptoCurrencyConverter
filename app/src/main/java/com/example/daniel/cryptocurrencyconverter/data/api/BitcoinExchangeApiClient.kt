package com.example.daniel.cryptocurrencyconverter.data.api

import com.example.daniel.cryptocurrencyconverter.data.models.CryptoExchangeRateRaw
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class BitcoinExchangeApiClient @Inject constructor(bitcoinRateService: BitcoinRateService) {
    var mBitcoinRateService = bitcoinRateService

    fun fetch(): Observable<CryptoExchangeRateRaw>{
        return mBitcoinRateService.getBitcoinRate()
    }


}