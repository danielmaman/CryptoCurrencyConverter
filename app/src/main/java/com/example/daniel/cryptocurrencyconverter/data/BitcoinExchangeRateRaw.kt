package com.example.daniel.cryptocurrencyconverter.data

data class BitcoinExchangeRateRaw(val chartName: String = "",
                     val bpi: Bpi,
                     val time: Time,
                     val disclaimer: String = "")