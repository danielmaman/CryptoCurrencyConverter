package com.example.daniel.cryptocurrencyconverter.presentation.main

import android.support.v7.app.AppCompatDelegate
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.daniel.cryptocurrencyconverter.R
import com.example.daniel.cryptocurrencyconverter.base.BaseApplication
import com.example.daniel.cryptocurrencyconverter.base.BaseController
import com.example.daniel.cryptocurrencyconverter.common.dagger.ApiModule
import com.example.daniel.cryptocurrencyconverter.common.dagger.AppModule
import com.example.daniel.cryptocurrencyconverter.common.dagger.DaggerAppComponent
import com.example.daniel.cryptocurrencyconverter.data.models.BitcoinExchangeRateRaw
import com.example.daniel.cryptocurrencyconverter.data.BitcoinRateRepository
import com.example.daniel.cryptocurrencyconverter.presentation.adapters.CurrencySpinnerAdapter
import com.example.daniel.cryptocurrencyconverter.presentation.models.AvailableCurrency
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import org.joda.money.BigMoney
import org.joda.money.CurrencyUnit
import timber.log.Timber
import javax.inject.Inject

class  MainController : BaseController() , MainViewDelegate {
    lateinit var view: MainView

    @Inject
    lateinit var repo: BitcoinRateRepository

    @Inject
    lateinit var compositeDisposable: CompositeDisposable

    override fun onCreateControllerView(inflater: LayoutInflater, container: ViewGroup): View {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

        val appComponent = DaggerAppComponent.builder()
                .apiModule(ApiModule())
                .appModule(AppModule(applicationContext as BaseApplication))
                .build().inject(this)

        view = MainView(activity!!, null)
        view.delegate = this

        view.attachSpinnersAdapter(getCurrencySpinnersAdapter())//TODO MAKE VIEW ON CREATE
        view.attachListeners()

        return view
    }

    override fun onRefreshTapped() {

                var obs: Observable<BitcoinExchangeRateRaw> = repo.fetch()
                compositeDisposable.add(
                obs.subscribeWith(object : DisposableObserver<BitcoinExchangeRateRaw>(){
                            override fun onComplete() {
                               // Toast.makeText(activity,"onComplete",Toast.LENGTH_LONG).show()
                                Timber.e("onComplete")
                            }

                            override fun onNext(t: BitcoinExchangeRateRaw) {
                              // Toast.makeText(activity,"OnNext",Toast.LENGTH_LONG).show()

                                Toast.makeText(activity,"timespawn"+t.timestamp, Toast.LENGTH_LONG).show()
                                Timber.e("onNext")
                            }

                            override fun onError(e: Throwable) {
                             //  Toast.makeText(activity,"onError",Toast.LENGTH_LONG).show()
                                Timber.e("onError")
                            }
                        }))
    }

    override fun onExchangeDataChanged(sell: BigMoney, toCurrency: CurrencyUnit) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun getCurrencySpinnersAdapter(): CurrencySpinnerAdapter {
        val availableCurrencyUnit = AvailableCurrency(activity!!)
        return CurrencySpinnerAdapter(availableCurrencyUnit, activity!!)//TODO injection
    }

    override fun onDestroy() {
        compositeDisposable.clear()
    }
}