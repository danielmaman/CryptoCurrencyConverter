package com.example.daniel.cryptocurrencyconverter.presentation.main.models

class DisplayableCurrency constructor(val amount: String, private var currencyCode: String){

    override fun toString(): String {
        super.toString()
        return amount.substring(0,amount.length-2) +" "+ currencyCode + " = 1฿"
    }
}