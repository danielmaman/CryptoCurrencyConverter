package com.example.daniel.cryptocurrencyconverter.data

import com.example.daniel.cryptocurrencyconverter.data.models.CryptoExchangeRateRaw
import io.realm.Realm
import timber.log.Timber
import kotlin.math.floor

class CryptoRateDbRepository {
    private val realmInstance: Realm = Realm.getDefaultInstance()
    private var realmResult: CryptoExchangeRateRaw? = null

    fun saveToRealmDB(t: CryptoExchangeRateRaw){
        try {
            realmInstance.beginTransaction()
            t.bpi!!.EUR!!.rate_float = floor(t.bpi!!.EUR!!.rate_float * 100) /100
            t.bpi!!.USD!!.rate_float = floor(t.bpi!!.USD!!.rate_float * 100) /100
            t.bpi!!.GBP!!.rate_float = floor(t.bpi!!.GBP!!.rate_float * 100) /100
            realmInstance.insertOrUpdate(t)
            realmInstance.commitTransaction()
        } catch (ex: Exception) {
            Timber.d(ex.toString())
        }

    }

    fun getLastSavedDatabaseInstance():CryptoExchangeRateRaw?{
          realmInstance.executeTransaction {
              realmResult = realmInstance.where(CryptoExchangeRateRaw::class.java).equalTo("chartName","Bitcoin").findFirst()
              if (realmResult!=null){
                  val tempObject = CryptoExchangeRateRaw(realmResult!!.chartName, realmResult!!.bpi, realmResult!!.time, realmResult!!.disclaimer)
                  tempObject.timestamp = realmResult!!.timestamp
                  realmResult= tempObject
              }
          }
          return realmResult
    }
}