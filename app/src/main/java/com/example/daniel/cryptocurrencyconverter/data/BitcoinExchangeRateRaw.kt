package com.example.daniel.cryptocurrencyconverter.data

import timber.log.Timber

/**
 * Created by Daniel on 3/20/2018.
 */
//class BitcoinExchangeRateRaw constructor(updated:String ,code: String, rate: String,rate_float: String) {
//
//}
data class BitcoinExchangeRateRaw(val chartName: String = "",
                     val bpi: Bpi,
                     val time: Time,
                     val disclaimer: String = "")