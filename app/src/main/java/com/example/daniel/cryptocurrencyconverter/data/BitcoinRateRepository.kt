package com.example.daniel.cryptocurrencyconverter.data

import com.example.daniel.cryptocurrencyconverter.data.api.BitcoinExchangeApiClient
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class BitcoinRateRepository(apiClient : BitcoinExchangeApiClient,draftMapper : BitcoinRateDraftMapper) {

    var mApiClient = apiClient
    var mDraftMapper = draftMapper

    var isNewlyFetched: Boolean = false// TODO if false fetch else get from Realm


    fun fetch(): Observable<BitcoinExchangeRateRaw>{//TODO save raw object to cache and check if 1 minute elapsed
        return mApiClient.fetch() //TODO remove
    }

    fun fetchFromApi(): Observable<BitcoinExchangeRateRaw>{
        val rawResult = mApiClient.fetch()
        rawResult.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(object:Observer<BitcoinExchangeRateRaw>{
                    override fun onComplete() {
                        Timber.e("onComplete")
                    }

                    override fun onSubscribe(d: Disposable) {
                        Timber.e("onSubscribe")
                    }

                    override fun onNext(t: BitcoinExchangeRateRaw) {
                        Timber.e("onNext")
                    }

                    override fun onError(e: Throwable) {
                        Timber.e("onError")
                    }
                })
        return rawResult
        //TODO change value to true and init timer to chagne to false after a minute
    }

//    fun fetchFromDB(): Observable<BitcoinExchangeRateRaw>{
//
//    }
}