package com.example.daniel.cryptocurrencyconverter.data

import com.example.daniel.cryptocurrencyconverter.data.api.BitcoinExchangeApiClient
import com.example.daniel.cryptocurrencyconverter.data.models.CryptoExchangeRateRaw
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import timber.log.Timber


class CryptoRateRepository(apiClient : BitcoinExchangeApiClient) {

    var mApiClient = apiClient

    private var realmInstance: Realm = Realm.getDefaultInstance()
    private var realmResult: CryptoExchangeRateRaw? = null

    fun fetch():Observable<CryptoExchangeRateRaw>{
        realmResult =null
        fetchDatabase()

        if (realmResult!=null && realmResult!!.isUpToDate()){
            return Observable.just(realmResult!!).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
        }else {
            return mApiClient.fetch().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).doOnNext{t ->  saveToRealmDB(t)}
        }

    }

    private fun saveToRealmDB(t: CryptoExchangeRateRaw){
        try {
            realmInstance.beginTransaction()
            realmInstance.insertOrUpdate(t)
            realmInstance.commitTransaction()
        } catch (ex: Exception) {
            Timber.d(ex.toString())
        }

    }
    private fun fetchDatabase(){
          realmInstance.executeTransaction {
              realmResult = realmInstance.where(CryptoExchangeRateRaw::class.java).equalTo("chartName","Bitcoin").findFirst()
              if (realmResult!=null){
                  val tempObject = CryptoExchangeRateRaw(realmResult!!.chartName, realmResult!!.bpi, realmResult!!.time, realmResult!!.disclaimer)
                  tempObject.timestamp = realmResult!!.timestamp
                  realmResult= tempObject
              }
          }
    }
}