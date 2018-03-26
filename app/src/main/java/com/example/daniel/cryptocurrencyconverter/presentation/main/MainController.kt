package com.example.daniel.cryptocurrencyconverter.presentation.main

import android.support.v7.app.AppCompatDelegate
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class  MainController : BaseController() , MainViewDelegate {
    lateinit var view: MainView

    @Inject
    lateinit var repo: BitcoinRateRepository

    @Inject
    lateinit var compositeDisposable: CompositeDisposable

    lateinit var lastBitcoinExchangeRate: BitcoinExchangeRateRaw

    override fun onCreateControllerView(inflater: LayoutInflater, container: ViewGroup): View {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

        val appComponent = DaggerAppComponent.builder()
                .apiModule(ApiModule())
                .appModule(AppModule(applicationContext as BaseApplication))
                .build().inject(this)

        view = MainView(activity!!, null)
        view.delegate = this

        view.attachSpinnersAdapter(getCurrencySpinnerAdapter(), getCurrencySpinnerAdapter(true))
        view.attachListeners()

        refreshRates()

        return view
    }

    fun refreshRates(){
        val availableCurrencyUnit = AvailableCurrency(activity!!)//TODO injection and refactor to kotlin way
        val obs: Observable<BitcoinExchangeRateRaw> = repo.fetch()//TODO loading indicator
        compositeDisposable.add(
                obs.subscribeWith(object : DisposableObserver<BitcoinExchangeRateRaw>(){
                    override fun onComplete() {
                        // Toast.makeText(activity,"onComplete",Toast.LENGTH_LONG).show()
                        Timber.e("onComplete")
                    }

                    override fun onNext(t: BitcoinExchangeRateRaw) {
                        lastBitcoinExchangeRate= t
                        val adapter = BitcoinRatesRecyclerViewAdapter(DisplayableItemMapper.mapRawItem(t),availableCurrencyUnit)
                        view.attachRecyclerViewAdapter(adapter)
                        val string = activity!!.resources.getString(R.string.updated)
                        view.updateViewAfterRatesRefresh(t.chartName, string +" "+getLocalTimeFromUTC(t.time?.updated))

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

    override fun onExchangeDataChanged(sell: BigMoney, toCurrency: CurrencyUnit) {//TODO change for easier currency extendability kad reiktu pakeisti tik arrays
        var exchangeRate = 0F
        var amount =""
        try {
            if (toCurrency.code == "BTC"){

                when(sell.currencyUnit.code){
                    "EUR" ->  exchangeRate= lastBitcoinExchangeRate.bpi!!.EUR!!.rate_float

                    "USD" ->  exchangeRate= lastBitcoinExchangeRate.bpi!!.USD!!.rate_float

                    "GBP" ->  exchangeRate= lastBitcoinExchangeRate.bpi!!.GBP!!.rate_float
                }
                val temp= sell.amount.toFloat() / exchangeRate
                amount = temp.formatPrecision(8 )

            }else{

                when(toCurrency.code){
                    "EUR" ->  exchangeRate= lastBitcoinExchangeRate.bpi!!.EUR!!.rate_float

                    "USD" ->  exchangeRate= lastBitcoinExchangeRate.bpi!!.USD!!.rate_float

                    "GBP" ->  exchangeRate= lastBitcoinExchangeRate.bpi!!.GBP!!.rate_float
                }
                val temp= sell.amount.toFloat() * exchangeRate
                amount = temp.formatPrecision(2)

            }
        }catch (e: NullPointerException){
            //in real app crashanalytics and report to user that some error
        }finally {
            view.updateExchangeResultTextView(amount)//TODO presiciob
        }

    }

    private fun getCurrencySpinnerAdapter(cryptoCurrencies: Boolean = false): CurrencySpinnerAdapter {
        val availableCurrencyUnit = AvailableCurrency(activity!!, cryptoCurrencies)//TODO injection
        return CurrencySpinnerAdapter(availableCurrencyUnit, activity!!)
    }

    private fun Float.formatPrecision(fracDigits: Int): String {
        val df = DecimalFormat()
        df.maximumFractionDigits = fracDigits
        return df.format(this)
    }

    override fun onDestroy() {
        compositeDisposable.clear()
    }
}