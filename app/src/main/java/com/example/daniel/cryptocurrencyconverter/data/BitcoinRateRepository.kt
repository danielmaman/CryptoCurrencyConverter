package com.example.daniel.cryptocurrencyconverter.data

import com.example.daniel.cryptocurrencyconverter.common.dagger.ApiModule
import com.example.daniel.cryptocurrencyconverter.common.dagger.AppModule
import com.example.daniel.cryptocurrencyconverter.common.dagger.DaggerAppComponent
import com.example.daniel.cryptocurrencyconverter.data.api.BitcoinExchangeApiClient
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit

class BitcoinRateRepository(apiClient : BitcoinExchangeApiClient,draftMapper : BitcoinRateDraftMapper, compositeDisposable: CompositeDisposable) {

    var mApiClient = apiClient
    var mDraftMapper = draftMapper
    var mCompositeDisposable =compositeDisposable
    var isNewlyFetched: Boolean = false// TODO if false fetch else get from Realm

    lateinit var completable: Completable

    fun fetch(): Observable<BitcoinExchangeRateRaw>{//TODO save raw object to cache and check if 1 minute elapsed
        return if (isNewlyFetched){
            fetchFromApi()//TODO add realm fetch
        }else{
            fetchFromApi()
        }
    }

    fun fetchFromApi(): Observable<BitcoinExchangeRateRaw>{
        val rawResult = mApiClient.fetch()


        completable = Completable.timer(1, TimeUnit.MINUTES, AndroidSchedulers.mainThread())


        rawResult.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(object:Observer<BitcoinExchangeRateRaw>{
                    override fun onComplete() {
                        isNewlyFetched = true
                        Timber.e("onComplete")
                        completable.subscribe{ isNewlyFetched = false}
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

    fun fetchFromDB(): Observable<BitcoinExchangeRateRaw>{
        return fetchFromApi()//TODO
    }

    fun temp(){
        isNewlyFetched = !isNewlyFetched
    }

}