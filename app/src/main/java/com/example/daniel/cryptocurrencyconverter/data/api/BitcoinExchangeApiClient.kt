package com.example.daniel.cryptocurrencyconverter.data.api

import com.example.daniel.cryptocurrencyconverter.data.BitcoinExchangeRateRaw
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class BitcoinExchangeApiClient @Inject constructor(bitcoinRateService: BitcoinRateService) {//TODO inject
    var mBitcoinRateService = bitcoinRateService

    fun fetch(): Observable<BitcoinExchangeRateRaw>{
        return mBitcoinRateService.getBitcoinRate()
    }


}