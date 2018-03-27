package com.example.daniel.cryptocurrencyconverter.domain

import com.example.daniel.cryptocurrencyconverter.base.BaseApplication
import com.example.daniel.cryptocurrencyconverter.common.dagger.ApiModule
import com.example.daniel.cryptocurrencyconverter.common.dagger.AppModule
import com.example.daniel.cryptocurrencyconverter.common.dagger.DaggerAppComponent
import com.example.daniel.cryptocurrencyconverter.data.CryptoRateApiRepository
import com.example.daniel.cryptocurrencyconverter.data.CryptoRateDbRepository
import com.example.daniel.cryptocurrencyconverter.data.models.CryptoExchangeRateRaw
import com.example.daniel.cryptocurrencyconverter.domain.mappers.DraftRatesItemMapper
import com.example.daniel.cryptocurrencyconverter.presentation.main.models.CryptoExchangeRateDraft
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class RepositoryInteractor(baseApplication: BaseApplication) : MvpInteractor {

    @Inject
    lateinit var apiRepository : CryptoRateApiRepository

    @Inject
    lateinit var dbRepository : CryptoRateDbRepository


    init {
        val appComponent = DaggerAppComponent.builder()
                .apiModule(ApiModule())
                .appModule(AppModule(baseApplication))
                .build().inject(this)
    }

    private fun fetch(): Observable<CryptoExchangeRateRaw> {
        val realmResult = dbRepository.getLastSavedDatabaseInstance()

        return if (realmResult!=null && realmResult.isUpToDate()){
            Observable.just(realmResult).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
        }else {
            apiRepository.fetchBitcoinRates().doOnNext{ t ->  dbRepository.saveToRealmDB(t)}
        }

    }

    override fun refreshRates():Observable<CryptoExchangeRateDraft>{
        val obs: Observable<CryptoExchangeRateRaw> = fetch()
        return  obs.map { it -> DraftRatesItemMapper.map(it) }
    }
}