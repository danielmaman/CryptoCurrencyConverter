package com.example.daniel.cryptocurrencyconverter.presentation.main.mappers

import android.os.Build
import android.text.Html
import com.example.daniel.cryptocurrencyconverter.presentation.main.models.CryptoExchangeRateDraft
import com.example.daniel.cryptocurrencyconverter.presentation.main.models.DisplayableCurrency
import timber.log.Timber

class DisplayableItemMapper {

    companion object {
        fun mapDraftItem(rawItem: CryptoExchangeRateDraft):ArrayList<DisplayableCurrency>{

            val currencies = arrayListOf<DisplayableCurrency>()
            try {

                rawItem.currencies.forEach {
                    if (it != null) {
                        currencies.add(DisplayableCurrency(it.rate, getStringSymbolFromHtml(it.symbol)))
                    }
                }
            }catch (e: NullPointerException){
                Timber.e(e)
                // in production app add crash analytics
                // SHOW USER ERROR TOAST
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
