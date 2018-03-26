package com.example.daniel.cryptocurrencyconverter.presentation.main.models


class DisplayableCurrency constructor(amount: String, private var currencyCode: String){

    var amount: String = amount.removeRange(amount.length-3,amount.lastIndex)

    override fun toString(): String {
        super.toString()
        return "$amount $currencyCode = 1฿"
    }

}