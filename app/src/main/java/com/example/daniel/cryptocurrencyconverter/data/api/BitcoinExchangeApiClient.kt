package com.example.daniel.cryptocurrencyconverter.data.api

import io.reactivex.Observable
import com.example.daniel.cryptocurrencyconverter.data.BitcoinExchangeRateRaw
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class BitcoinExchangeApiClient @Inject constructor(bitcoinRateService: BitcoinRateService) {//TODO inject
    var mBitcoinRateService = bitcoinRateService

    fun fetch(): Observable<BitcoinExchangeRateRaw>{
        return mBitcoinRateService.getBitcoinRate()
    }
}