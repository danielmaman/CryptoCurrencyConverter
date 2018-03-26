package com.example.daniel.cryptocurrencyconverter.common.mappers

import android.os.Build
import android.text.Html
import com.example.daniel.cryptocurrencyconverter.data.models.BitcoinExchangeRateRaw
import com.example.daniel.cryptocurrencyconverter.presentation.main.models.DisplayableCurrency
import timber.log.Timber

class DisplayableItemMapper {

    companion object {
        fun mapRawItem(rawItem: BitcoinExchangeRateRaw):MutableList<DisplayableCurrency>{

            val currencies = mutableListOf<DisplayableCurrency>()
            try {
                currencies.add(DisplayableCurrency(rawItem.bpi!!.EUR!!.rate, getStringSymbolFromHtml(rawItem.bpi!!.EUR!!.symbol)))//todo for easier extendability for each
                currencies.add(DisplayableCurrency(rawItem.bpi!!.USD!!.rate, getStringSymbolFromHtml(rawItem.bpi!!.USD!!.symbol)))
                currencies.add(DisplayableCurrency(rawItem.bpi!!.GBP!!.rate, getStringSymbolFromHtml(rawItem.bpi!!.GBP!!.symbol)))
            }catch (e: NullPointerException){
                Timber.e(e)//TODO in production app add crash analytics
                //TODO SHOW USER ERROR TOAST move to controller
            }

            return currencies
        }

        private fun getStringSymbolFromHtml(symbol :String):String{
            return if (Build.VERSION.SDK_INT >= 24) {
                Html.fromHtml( symbol, Html.FROM_HTML_MODE_LEGACY).toString()
            } else {
                Html.fromHtml( symbol ).toString()
            }
        }
    }


}
