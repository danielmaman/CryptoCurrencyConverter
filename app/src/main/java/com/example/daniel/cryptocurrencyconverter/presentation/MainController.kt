package com.example.daniel.cryptocurrencyconverter.presentation

import android.support.v7.app.AppCompatDelegate
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.daniel.cryptocurrencyconverter.base.BaseController
import com.example.daniel.cryptocurrencyconverter.common.dagger.ApiModule
import com.example.daniel.cryptocurrencyconverter.common.dagger.DaggerAppComponent
import com.example.daniel.cryptocurrencyconverter.data.BitcoinRateDraftMapper
import com.example.daniel.cryptocurrencyconverter.data.BitcoinRateRepository
import com.example.daniel.cryptocurrencyconverter.data.api.BitcoinExchangeApiClient
import com.example.daniel.cryptocurrencyconverter.presentation.adapters.CurrencySpinnerAdapter
import com.example.daniel.cryptocurrencyconverter.presentation.models.AvailableCurrency
import org.joda.money.BigMoney
import org.joda.money.CurrencyUnit
import javax.inject.Inject

class  MainController : BaseController() , MainViewDelegate{
    lateinit var view: MainView

    @Inject
    lateinit var apiClient: BitcoinExchangeApiClient

    @Inject
    lateinit var bitcoinRateDraftMapper: BitcoinRateDraftMapper

    override fun onCreateControllerView(inflater: LayoutInflater, container: ViewGroup): View {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

        val appComponent = DaggerAppComponent.builder()
                .apiModule(ApiModule())
                .build()
        appComponent.inject(this)

        view = MainView(activity!!, null)
        view.delegate = this

        view.attachSpinnersAdapter(getCurrencySpinnersAdapter())

        var repo= BitcoinRateRepository(apiClient,bitcoinRateDraftMapper)//TODO

        repo.fetch()

        return view
    }

    override fun onExchangeDataChanged(sell: BigMoney, toCurrency: CurrencyUnit) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun getCurrencySpinnersAdapter(): CurrencySpinnerAdapter {
        val availableCurrencyUnit = AvailableCurrency(activity!!)
        return CurrencySpinnerAdapter(availableCurrencyUnit, activity!!)//TODO injection
    }
}