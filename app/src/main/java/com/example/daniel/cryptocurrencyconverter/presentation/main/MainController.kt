package com.example.daniel.cryptocurrencyconverter.presentation.main

import android.support.v7.app.AppCompatDelegate
import android.support.v7.widget.LinearLayoutManager
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
import com.example.daniel.cryptocurrencyconverter.common.mappers.DisplayableItemMapper
import com.example.daniel.cryptocurrencyconverter.data.models.BitcoinExchangeRateRaw
import com.example.daniel.cryptocurrencyconverter.data.BitcoinRateRepository
import com.example.daniel.cryptocurrencyconverter.presentation.adapters.CurrencySpinnerAdapter
import com.example.daniel.cryptocurrencyconverter.presentation.main.adapters.BitcoinRatesRecyclerViewAdapter
import com.example.daniel.cryptocurrencyconverter.presentation.models.AvailableCurrency
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import org.joda.money.BigMoney
import org.joda.money.CurrencyUnit
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
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

        requestRates()

        return view
    }

    override fun onRefreshTapped() {
        requestRates()
    }

    fun requestRates(){
        val availableCurrencyUnit = AvailableCurrency(activity!!)//TODO injection and refactor to kotlin way
        var obs: Observable<BitcoinExchangeRateRaw> = repo.fetch()//TODO loading indicator
        compositeDisposable.add(
                obs.subscribeWith(object : DisposableObserver<BitcoinExchangeRateRaw>(){
                    override fun onComplete() {
                        // Toast.makeText(activity,"onComplete",Toast.LENGTH_LONG).show()
                        Timber.e("onComplete")
                    }

                    override fun onNext(t: BitcoinExchangeRateRaw) {

                        var adapter = BitcoinRatesRecyclerViewAdapter(DisplayableItemMapper.mapRawItem(t),availableCurrencyUnit)
                        view.attachRecyclerViewAdapter(adapter)

                        view.updateViewAfterRatesRefresh(t.chartName, getLocalTimeFromUTC(t.time?.updated))

                        Timber.e("onNext")
                    }

                    override fun onError(e: Throwable) {
                        //  Toast.makeText(activity,"onError",Toast.LENGTH_LONG).show()
                        Timber.e("onError")
                    }
                }))
    }

    private fun getLocalTimeFromUTC(utcTime: String?): String{
        if (utcTime!=null){
            val df = SimpleDateFormat("MMM dd, yyyy HH:mm:ss", Locale.ENGLISH)
            df.timeZone = TimeZone.getTimeZone("UTC")
            val date = df.parse(utcTime)
            val df1 = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.ENGLISH)
            df1.timeZone = TimeZone.getDefault()
            return df1.format(date)
        }
        return ""
    }

    override fun onExchangeDataChanged(sell: BigMoney, toCurrency: CurrencyUnit) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun getCurrencySpinnersAdapter(): CurrencySpinnerAdapter {
        val availableCurrencyUnit = AvailableCurrency(activity!!)//TODO injection
        return CurrencySpinnerAdapter(availableCurrencyUnit, activity!!)
    }

    override fun onDestroy() {
        compositeDisposable.clear()
    }
}