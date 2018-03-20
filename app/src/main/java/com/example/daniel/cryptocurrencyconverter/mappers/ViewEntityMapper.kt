package com.example.daniel.cryptocurrencyconverter.mappers

import com.example.daniel.cryptocurrencyconverter.presentation.models.DisplayableCurrency
import org.joda.money.Money

/**
 * Created by Daniel on 3/20/2018.
 */
class ViewEntityMapper {
    companion object {
        fun map(currencyRatesDraft: MutableList<Money>):MutableList<DisplayableCurrency>{

            val displayableCurrencyList = mutableListOf<DisplayableCurrency>()

            currencyRatesDraft.forEach{value ->
                displayableCurrencyList.add(DisplayableCurrency(value))
            }
            return displayableCurrencyList
        }
    }
}