package com.example.daniel.cryptocurrencyconverter.data

import com.example.daniel.cryptocurrencyconverter.data.api.BitcoinExchangeApiClient
import com.example.daniel.cryptocurrencyconverter.data.models.BitcoinExchangeRateRaw
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import timber.log.Timber


class BitcoinRateRepository(apiClient : BitcoinExchangeApiClient) {

    var mApiClient = apiClient

    private var realmInstance: Realm = Realm.getDefaultInstance()
    private var realmResult: BitcoinExchangeRateRaw? = null

    fun fetch():Observable<BitcoinExchangeRateRaw>{
        realmResult =null
        fetchDatabase()

        if (realmResult!=null && realmResult!!.isUpToDate()){
            return Observable.just(realmResult!!).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
        }else {
            return mApiClient.fetch().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).doOnNext{t ->  saveToRealmDB(t)}
        }

//        var exchange : Observable<BitcoinExchangeRateRaw> = Observable
//                .concat( Observable.just(realmResult).
//                                 .observeOn(AndroidSchedulers.mainThread())
//                                 .subscribeOn(Schedulers.io()),
//                        mApiClient.fetch()
//                                 .observeOn(AndroidSchedulers.mainThread())
//                                 .subscribeOn(Schedulers.io()))
//                .filter { t ->  t.isUpToDate()}.take(1).firstOrError().toObservable()
        //return null
    }

    private fun saveToRealmDB(t: BitcoinExchangeRateRaw){
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
              realmResult = realmInstance.where(BitcoinExchangeRateRaw::class.java).equalTo("chartName","Bitcoin").findFirst()
              if (realmResult!=null){
                  val tempObject = BitcoinExchangeRateRaw(realmResult!!.chartName, realmResult!!.bpi, realmResult!!.time, realmResult!!.disclaimer)
                  tempObject.timestamp = realmResult!!.timestamp
                  realmResult= tempObject
              }
          }
    }
}