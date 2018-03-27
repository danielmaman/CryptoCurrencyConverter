package com.example.daniel.cryptocurrencyconverter.presentation.main.models

import com.example.daniel.cryptocurrencyconverter.data.models.CurrencyUnitRaw
import com.example.daniel.cryptocurrencyconverter.data.models.Time
import java.sql.Timestamp

class CryptoExchangeRateDraft(var chartName: String = "",
                              var time: Time? = null,
                              var disclaimer: String = "",
                              var timestamp: Long = 0,
                              var currencies: ArrayList<CurrencyUnitRaw?> = arrayListOf())