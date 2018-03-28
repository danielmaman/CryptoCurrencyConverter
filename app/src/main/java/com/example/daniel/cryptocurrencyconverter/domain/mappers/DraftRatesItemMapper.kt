package com.example.daniel.cryptocurrencyconverter.domain.mappers

import com.example.daniel.cryptocurrencyconverter.data.models.CryptoExchangeRateRaw
import com.example.daniel.cryptocurrencyconverter.presentation.main.models.CryptoExchangeRateDraft

class DraftRatesItemMapper {

    companion object {
        fun map(rawRates: CryptoExchangeRateRaw):CryptoExchangeRateDraft{
            val draftObject = CryptoExchangeRateDraft(rawRates.chartName,rawRates.time,rawRates.disclaimer, rawRates.timestamp)
            draftObject.currencies.add(rawRates.bpi?.EUR)
            draftObject.currencies.add(rawRates.bpi?.USD)
            draftObject.currencies.add(rawRates.bpi?.GBP)
            return draftObject
        }
    }

}