package com.example.daniel.cryptocurrencyconverter.data.models

import io.realm.RealmModel
import io.realm.annotations.RealmClass

@RealmClass
open class Time(open var updateduk: String = "",
                open var updatedISO: String = "",
                open var updated: String = ""): RealmModel