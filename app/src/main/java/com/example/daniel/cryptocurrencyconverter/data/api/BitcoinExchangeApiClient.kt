package com.example.daniel.cryptocurrencyconverter.data.api

import com.example.daniel.cryptocurrencyconverter.data.models.BitcoinExchangeRateRaw
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class BitcoinExchangeApiClient @Inject constructor(bitcoinRateService: BitcoinRateService) {//TODO inject
    var mBitcoinRateService = bitcoinRateService

    fun fetch(): Observable<BitcoinExchangeRateRaw>{
        return mBitcoinRateService.getBitcoinRate()
    }


}