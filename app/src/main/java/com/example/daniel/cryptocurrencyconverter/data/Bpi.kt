package com.example.daniel.cryptocurrencyconverter.data

import io.realm.RealmObject

open class Bpi(public open var EUR: CurrencyUnitRaw? = null,
               public open var GBP: CurrencyUnitRaw? = null,
               public open var USD: CurrencyUnitRaw? = null): RealmObject()