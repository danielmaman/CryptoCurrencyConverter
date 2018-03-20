package com.example.daniel.cryptocurrencyconverter.data

import android.util.Log
import com.example.daniel.cryptocurrencyconverter.common.dagger.ApiModule
import com.example.daniel.cryptocurrencyconverter.common.dagger.DaggerAppComponent
import com.example.daniel.cryptocurrencyconverter.data.api.BitcoinExchangeApiClient
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import timber.log.Timber
import javax.inject.Inject


class BitcoinRateRepository constructor(apiClient: BitcoinExchangeApiClient, bitcoinRateDraftMapper: BitcoinRateDraftMapper) {
    val mApiClient = apiClient
    val mDraftMapper = bitcoinRateDraftMapper

    var isNewlyFetched: Boolean = false

    fun fetch(){//TODO save raw object to cache and check if 1 minute elapsed
        val rawResult = mApiClient.fetch()
        rawResult.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(object:Observer<BitcoinExchangeRateRaw>{
                    override fun onComplete() {
                        Timber.e("onCompltet")
                    }

                    override fun onSubscribe(d: Disposable) {
                        Timber.e("onSubsrice")
                    }

                    override fun onNext(t: BitcoinExchangeRateRaw) {
                        Timber.e("onNext")
                    }

                    override fun onError(e: Throwable) {
                        Timber.e("onError")
                    }
                })
    }
}