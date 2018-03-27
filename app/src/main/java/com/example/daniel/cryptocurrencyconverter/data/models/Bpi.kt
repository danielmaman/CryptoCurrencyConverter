package com.example.daniel.cryptocurrencyconverter.data.models

import io.realm.RealmObject

open class Bpi(open var EUR: CurrencyUnitRaw? = null,
               open var GBP: CurrencyUnitRaw? = null,
               open var USD: CurrencyUnitRaw? = null): RealmObject()