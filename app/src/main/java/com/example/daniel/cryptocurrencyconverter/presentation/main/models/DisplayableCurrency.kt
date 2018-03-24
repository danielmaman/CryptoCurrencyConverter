package com.example.daniel.cryptocurrencyconverter.presentation.models

import com.example.daniel.cryptocurrencyconverter.data.models.BitcoinExchangeRateRaw
import org.joda.money.Money

class DisplayableCurrency constructor(result: BitcoinExchangeRateRaw){

    var eur: Money= Money.parse(result.bpi?.EUR?.code+result.bpi?.EUR?.rate)
    var usd: Money= Money.parse(result.bpi?.USD?.code+result.bpi?.USD?.rate)
    var gbp: Money= Money.parse(result.bpi?.USD?.code+result.bpi?.USD?.rate)
//    var amount: String = "%.${2}f".format(money.amount)
//    var currenyUnit: String = money.currencyUnit.code

//    override fun toString(): String {
//        //super.toString()
//        return super.toString()
//    }
}