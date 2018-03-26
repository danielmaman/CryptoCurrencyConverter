package com.example.daniel.cryptocurrencyconverter.presentation.models

import android.content.Context
import com.example.daniel.cryptocurrencyconverter.R
import org.joda.money.CurrencyUnit
import timber.log.Timber
import java.util.ArrayList

/**
 * Created by Daniel on 3/20/2018.
 */
class AvailableCurrency(context: Context, var cryptoCurrencies: Boolean = false) {//TODO inject

    val availableCurrencies = ArrayList<CurrencyUnit>()
    val flags = ArrayList<Int>()

    init {
        var currenciesUnits = context.resources.getStringArray(R.array.available_currencies)
        var currenciesFlags = context.resources.getStringArray(R.array.available_currencies_flags)

        if (cryptoCurrencies){
            currenciesUnits = context.resources.getStringArray(R.array.available_crypto_currencies)
            currenciesFlags = context.resources.getStringArray(R.array.available_crypto_currencies_flags)
        }

        try {
            if (currenciesFlags.size == currenciesUnits.size) {
                currenciesUnits.forEach {unit->
                    val currencyUnit = CurrencyUnit.of(unit.toUpperCase())
                    availableCurrencies.add(currencyUnit)

                    val index = availableCurrencies.indexOf(currencyUnit)
                    val resources = context.resources
                    val resourceId = resources.getIdentifier("@drawable/" + currenciesFlags[index], "drawable",
                            context.packageName)
                    flags.add(resourceId)
                }

            } else {
                throw IllegalArgumentException()
            }
        } catch (ex: IllegalArgumentException) {
            Timber.e("Flag and Unit arrays should be the same length")
        }

    }

}