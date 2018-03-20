package com.example.daniel.cryptocurrencyconverter.presentation.models

import org.joda.money.Money

class DisplayableCurrency constructor(money:Money){

    var amount: String = "%.${2}f".format(money.amount)
    var currenyUnit: String = money.currencyUnit.code

}