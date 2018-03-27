package com.example.daniel.cryptocurrencyconverter.data.models

import io.realm.RealmObject

open class Time(open var updateduk: String = "",
                open var updatedISO: String = "",
                open var updated: String = ""): RealmObject()