package com.example.daniel.cryptocurrencyconverter.presentation.main

import android.os.Bundle
import android.support.v7.app.AppCompatDelegate
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.daniel.cryptocurrencyconverter.base.BaseApplication
import com.example.daniel.cryptocurrencyconverter.base.BaseController
import com.example.daniel.cryptocurrencyconverter.common.dagger.ApiModule
import com.example.daniel.cryptocurrencyconverter.common.dagger.AppModule
import com.example.daniel.cryptocurrencyconverter.common.dagger.DaggerAppComponent
import com.example.daniel.cryptocurrencyconverter.domain.RepositoryInteractor
import com.example.daniel.cryptocurrencyconverter.presentation.main.adapters.CurrencySpinnerAdapter
import com.example.daniel.cryptocurrencyconverter.presentation.main.adapters.CryptoRatesRecyclerViewAdapter
import com.example.daniel.cryptocurrencyconverter.presentation.main.mappers.DisplayableItemMapper
import com.example.daniel.cryptocurrencyconverter.presentation.main.models.CryptoExchangeRateDraft
import com.example.daniel.cryptocurrencyconverter.presentation.main.models.AvailableCurrency
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import org.joda.money.BigMoney
import org.joda.money.CurrencyUnit
import timber.log.Timber
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.math.floor

class  MainController : BaseController() , MainViewDelegate {
    lateinit var view: MainView

    @Inject
    lateinit var compositeDisposable: CompositeDisposable

    @Inject
    lateinit var interactor: RepositoryInteractor

    val CHECK_IF_CONF_CHANGES ="check"

    var lastCryptoExchangeRate: CryptoExchangeRateDraft = CryptoExchangeRateDraft()

    lateinit var availableCurrencyUnit: AvailableCurrency

    lateinit var availableCryptoCurrencyUnit: AvailableCurrency

    override fun onCreateControllerView(inflater: LayoutInflater, container: ViewGroup): View {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

        val appComponent = DaggerAppComponent.builder()
                .apiModule(ApiModule())
                .appModule(AppModule(applicationContext as BaseApplication))
                .build().inject(this)

        view = MainView(activity!!, null)
        view.delegate = this

        availableCurrencyUnit = AvailableCurrency(applicationContext!!)
        availableCryptoCurrencyUnit = AvailableCurrency(applicationContext!!,true)

        view.attachListeners()

        if (args.isEmpty){
            view.attachSpinnersAdapter(getCurrencySpinnerAdapter(), getCurrencySpinnerAdapter(true))
            refreshRates()
        }
        return view
    }

    override fun onRestoreViewState(view: View, savedViewState: Bundle) {
        super.onRestoreViewState(view, savedViewState)

        val cryptoRates = savedViewState.getStringArrayList(this.view.RECYCLERVIEW_RATE_KEY)
        val adapter = CryptoRatesRecyclerViewAdapter(availableCurrencyUnit, cryptoRates)
        this.view.attachRecyclerViewAdapter(adapter)

        val isSellCrypto = savedViewState.getBoolean(this.view.SELL_CRYPTO_BOOLEAN_KEY)
        if (isSellCrypto){
            this.view.attachSpinnersAdapter(getCurrencySpinnerAdapter(true), getCurrencySpinnerAdapter())
        }else{
            this.view.attachSpinnersAdapter(getCurrencySpinnerAdapter(), getCurrencySpinnerAdapter(true))
        }

        this.view.onRestoreViewState(view,savedViewState)
    }

    override fun onSaveViewState(view: View, outState: Bundle) {
        super.onSaveViewState(view, outState)
        args.putBoolean(CHECK_IF_CONF_CHANGES, true)
        this.view.onSaveViewState(view,outState)
    }

    fun refreshRates(){
        val obs: Observable<CryptoExchangeRateDraft> = interactor.refreshRates()
        compositeDisposable.add(
                obs.subscribeWith(object : DisposableObserver<CryptoExchangeRateDraft>(){
                    override fun onComplete() {
                        Timber.e("onComplete")
                    }

                    override fun onNext(t: CryptoExchangeRateDraft) {
                        t.currencies.forEach {
                            if (it != null) {
                                it.rate_float = floor(it.rate_float * 100) /100 //format till 2 decimal places also may be round
                            }
                        }
                        lastCryptoExchangeRate= t
                        Toast.makeText(activity, t.timestamp.toString(),Toast.LENGTH_LONG).show()
                        val check =DisplayableItemMapper.mapDraftItem(t)
                        val adapter = CryptoRatesRecyclerViewAdapter(availableCurrencyUnit, check)
                        view.attachRecyclerViewAdapter(adapter)
                        view.updateViewAfterRatesRefresh(t.chartName, getLocalTimeFromUTC(t.time?.updated))
                        Timber.e("onNext")
                    }

                    override fun onError(e: Throwable) {

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
        var exchangeRate: Float
        var amount =""
        try {
            if (availableCryptoCurrencyUnit.availableCurrencies.contains(toCurrency)){//check if sell is a crypto

                 lastCryptoExchangeRate.currencies.forEach {
                     if (it != null && it.code == sell.currencyUnit.code) {
                         exchangeRate = it.rate_float
                         val temp= sell.amount.toFloat() / exchangeRate
                         amount = temp.formatPrecision(8 )
                     }
                 }

            }else{
                lastCryptoExchangeRate.currencies.forEach {
                    if (it != null && it.code == toCurrency.code) {
                        exchangeRate = it.rate_float
                        val temp= sell.amount.toFloat() * exchangeRate
                        amount = temp.formatPrecision(2 )
                    }
                }

            }
        }catch (e: NullPointerException){
            //in real app crashanalytics and report to user that some error
        }finally {
            view.updateExchangeResultTextView(amount)
        }
    }

    private fun getCurrencySpinnerAdapter(cryptoCurrencies: Boolean = false): CurrencySpinnerAdapter {
        return CurrencySpinnerAdapter(AvailableCurrency(activity!!, cryptoCurrencies), activity!!)
    }

    private fun Float.formatPrecision(fracDigits: Int): String {
        val df = DecimalFormat()
        df.maximumFractionDigits = fracDigits
        return df.format(this)
    }

    override fun onDestroy() {
        compositeDisposable.clear()//prevent memory leaks
    }
}