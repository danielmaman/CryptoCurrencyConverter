package com.example.daniel.cryptocurrencyconverter.presentation.models

import com.example.daniel.cryptocurrencyconverter.data.models.BitcoinExchangeRateRaw
import org.joda.money.Money

class DisplayableCurrency constructor(amount: String,currencyCode: String){

//    var eur: Money= Money.parse(result.bpi?.EUR?.code+result.bpi?.EUR?.rate)
//    var usd: Money= Money.parse(result.bpi?.USD?.code+result.bpi?.USD?.rate)
//    var gbp: Money= Money.parse(result.bpi?.USD?.code+result.bpi?.USD?.rate)
    var amount: String = amount //"%.${2}f".format(amount)//TODO format
    var currencyCode: String = currencyCode

//    override fun toString(): String {
//        //super.toString()
//        return super.toString()
//    }
}