package com.example.daniel.cryptocurrencyconverter.data

import com.example.daniel.cryptocurrencyconverter.data.models.CryptoExchangeRateRaw
import io.realm.Realm
import timber.log.Timber

class CryptoRateDbRepository {
    private val realmInstance: Realm = Realm.getDefaultInstance()
    private var realmResult: CryptoExchangeRateRaw? = null

    fun saveToRealmDB(t: CryptoExchangeRateRaw){
        try {
            realmInstance.beginTransaction()
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