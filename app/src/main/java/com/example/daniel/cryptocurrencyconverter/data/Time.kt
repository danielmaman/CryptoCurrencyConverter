package com.example.daniel.cryptocurrencyconverter.data

import io.realm.RealmObject

open class Time(public open var updateduk: String = "",
                public open var updatedISO: String = "",
                public open var updated: String = ""): RealmObject()