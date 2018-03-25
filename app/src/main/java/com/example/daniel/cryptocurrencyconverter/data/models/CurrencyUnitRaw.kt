package com.example.daniel.cryptocurrencyconverter.data.models

import io.realm.RealmObject

open class CurrencyUnitRaw( open var symbol: String = "",
                            open var rateFloat: Float = 0.0f,
                            open var code: String = "",
                            open var rate: String = "",
                            open var description: String = ""): RealmObject()