package com.example.daniel.cryptocurrencyconverter.common.mappers

import android.os.Build
import android.text.Html
import com.example.daniel.cryptocurrencyconverter.data.models.CryptoExchangeRateRaw
import com.example.daniel.cryptocurrencyconverter.presentation.main.models.DisplayableCurrency
import timber.log.Timber

class DisplayableItemMapper {

    companion object {
        fun mapRawItem(rawItem: CryptoExchangeRateRaw):MutableList<DisplayableCurrency>{

            val currencies = mutableListOf<DisplayableCurrency>()
            try {//todo for easier extendability for each Map to logic object in the interactor
                currencies.add(DisplayableCurrency(rawItem.bpi!!.EUR!!.rate, getStringSymbolFromHtml(rawItem.bpi!!.EUR!!.symbol)))
                currencies.add(DisplayableCurrency(rawItem.bpi!!.USD!!.rate, getStringSymbolFromHtml(rawItem.bpi!!.USD!!.symbol)))
                currencies.add(DisplayableCurrency(rawItem.bpi!!.GBP!!.rate, getStringSymbolFromHtml(rawItem.bpi!!.GBP!!.symbol)))
            }catch (e: NullPointerException){
                Timber.e(e)
                //TODO in production app add crash analytics
                //TODO SHOW USER ERROR TOAST
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
