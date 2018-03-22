package com.example.daniel.cryptocurrencyconverter.data

import com.example.daniel.cryptocurrencyconverter.data.api.BitcoinExchangeApiClient
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.realm.Realm

class BitcoinRateRepository(apiClient : BitcoinExchangeApiClient,draftMapper : BitcoinRateDraftMapper, compositeDisposable: CompositeDisposable) {

    var mApiClient = apiClient
    var mDraftMapper = draftMapper
    var mCompositeDisposable =compositeDisposable

    var newObject : Flowable<BitcoinExchangeRateRaw>? = null


    fun fetch():Observable<BitcoinExchangeRateRaw>{
        fetchDatabseFlowable()
        var exchange : Observable<BitcoinExchangeRateRaw> = Observable
                .concat(newObject?.toObservable()?.observeOn(AndroidSchedulers.mainThread())?.subscribeOn(Schedulers.io()),
                        mApiClient.fetch()?.observeOn(AndroidSchedulers.mainThread())?.subscribeOn(Schedulers.io())).filter { t -> t.isUpToDate()}.take(1)
        return exchange
    }

    fun saveToRealmDB(t: BitcoinExchangeRateRaw){
        //TODO
    }
    fun fetchDatabseFlowable(){

          val realmInstance =   Realm.getDefaultInstance()

          realmInstance.executeTransaction {
           val objectinsta = realmInstance.where(BitcoinExchangeRateRaw::class.java).findFirst()
              newObject = Flowable.just(objectinsta)
          }
    }
}