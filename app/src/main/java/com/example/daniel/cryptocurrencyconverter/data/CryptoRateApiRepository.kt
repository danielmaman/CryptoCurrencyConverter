package com.example.daniel.cryptocurrencyconverter.data

import com.example.daniel.cryptocurrencyconverter.data.api.BitcoinExchangeApiClient
import com.example.daniel.cryptocurrencyconverter.data.models.CryptoExchangeRateRaw
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CryptoRateApiRepository constructor(private val apiClient : BitcoinExchangeApiClient) {


    fun fetchBitcoinRates(): Observable<CryptoExchangeRateRaw> {
       return apiClient.fetch().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
    }
}