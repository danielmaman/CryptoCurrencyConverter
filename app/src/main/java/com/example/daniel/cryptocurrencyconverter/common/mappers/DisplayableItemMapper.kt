package com.example.daniel.cryptocurrencyconverter.common.mappers

import com.example.daniel.cryptocurrencyconverter.data.models.BitcoinExchangeRateRaw
import com.example.daniel.cryptocurrencyconverter.presentation.models.DisplayableCurrency
import org.joda.money.Money
import timber.log.Timber

class DisplayableItemMapper constructor(){

    companion object {
        fun mapRawItem(rawItem: BitcoinExchangeRateRaw):MutableList<DisplayableCurrency>{//TODO maybe different just string type??
//            var currencies = mutableListOf<Money>()
//            var eur = Money.parse(rawItem.bpi?.EUR?.code+" "+ rawItem.bpi?.EUR?.rate)
//            currencies.add(eur)
//            var usd = Money.parse(rawItem.bpi?.USD?.code+" "+ rawItem.bpi?.USD?.rate)
//            currencies.add(usd)
//            var gbp = Money.parse(rawItem.bpi?.GBP?.code+" "+ rawItem.bpi?.GBP?.rate)
//            currencies.add(gbp)
            var currencies = mutableListOf<DisplayableCurrency>()
            try {
                currencies.add(DisplayableCurrency(rawItem.bpi!!.EUR!!.rate,rawItem.bpi!!.EUR!!.code))
                currencies.add(DisplayableCurrency(rawItem.bpi!!.USD!!.rate,rawItem.bpi!!.USD!!.code))
                currencies.add(DisplayableCurrency(rawItem.bpi!!.GBP!!.rate,rawItem.bpi!!.GBP!!.code))
            }catch (e: NullPointerException){
                Timber.e(e)//TODO in production app add crash analytics
                //TODO SHOW USER ERROR TOAST move to controller
            }

            return currencies
        }
    }


}
