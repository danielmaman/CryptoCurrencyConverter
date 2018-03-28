package com.example.daniel.cryptocurrencyconverter.data.models

import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class CryptoExchangeRateRaw(
        @PrimaryKey
        open var chartName: String = "",
        open var bpi: Bpi? = null,
        open var time: Time? = null,
        open var disclaimer: String = ""): RealmModel{

    open var STALE_MS = (1 * 60 * 1000).toLong()//One minute in miliseconds in real app save to configurations
    open var timestamp: Long=0
    init {
        timestamp = System.currentTimeMillis()
    }

    fun isUpToDate():Boolean{
        return System.currentTimeMillis() - timestamp < STALE_MS
    }
}