package com.example.daniel.cryptocurrencyconverter.data

import io.realm.RealmObject
import io.realm.annotations.Ignore
import io.realm.annotations.PrimaryKey


open class BitcoinExchangeRateRaw(
                     @PrimaryKey
                     public open var chartName: String = "",
                     public open var bpi: Bpi? = null,
                     public open var time: Time? = null,
                     public open var disclaimer: String = ""): RealmObject(){

    @Ignore
    open var STALE_MS = (1 * 1000).toLong()
    @Ignore
    open var timestamp: Long?=null
    init {
        timestamp = System.currentTimeMillis()
    }

    fun isUpToDate():Boolean{
        return System.currentTimeMillis() - timestamp!! < STALE_MS
    }
}