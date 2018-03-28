package com.example.daniel.cryptocurrencyconverter.domain

import com.example.daniel.cryptocurrencyconverter.presentation.main.models.CryptoExchangeRateDraft

interface MvpInteractor {
    fun refreshRates(): io.reactivex.Observable<CryptoExchangeRateDraft>
}