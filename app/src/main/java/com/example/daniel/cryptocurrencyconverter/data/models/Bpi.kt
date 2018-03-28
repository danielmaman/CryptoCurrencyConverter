package com.example.daniel.cryptocurrencyconverter.data.models

import io.realm.RealmModel
import io.realm.annotations.RealmClass

@RealmClass
open class Bpi(open var EUR: CurrencyUnitRaw? = null,
               open var GBP: CurrencyUnitRaw? = null,
               open var USD: CurrencyUnitRaw? = null): RealmModel