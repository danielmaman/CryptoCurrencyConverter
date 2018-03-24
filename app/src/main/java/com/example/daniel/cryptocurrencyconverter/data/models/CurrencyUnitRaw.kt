package com.example.daniel.cryptocurrencyconverter.data.models

import io.realm.RealmObject

open class CurrencyUnitRaw(public open var symbol: String = "",
                           public open var rateFloat: Float = 0.0f,
                           public open var code: String = "",
                           public open var rate: String = "",
                           public open var description: String = ""): RealmObject()