package com.example.daniel.cryptocurrencyconverter.presentation.main.models

import com.example.daniel.cryptocurrencyconverter.data.models.CurrencyUnitRaw
import com.example.daniel.cryptocurrencyconverter.data.models.Time

class CryptoExchangeRateDraft(var chartName: String = "",
                              var time: Time? = null,
                              var disclaimer: String = "",
                              var currencies: ArrayList<CurrencyUnitRaw?> = arrayListOf())