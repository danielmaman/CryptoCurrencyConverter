package com.example.daniel.cryptocurrencyconverter.presentation

import android.content.Context
import android.support.v7.app.AppCompatDelegate
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.daniel.cryptocurrencyconverter.R
import com.example.daniel.cryptocurrencyconverter.base.BaseController
import com.example.daniel.cryptocurrencyconverter.presentation.models.AvailableCurrency
import org.joda.money.BigMoney
import org.joda.money.CurrencyUnit


/**
 * Created by Daniel on 3/20/2018.
 */
class  MainController : BaseController() , MainViewDelegate{
    lateinit var view: MainView
    lateinit var mAvailableCurrencyUnit: AvailableCurrency

    override fun onCreateControllerView(inflater: LayoutInflater, container: ViewGroup): View {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

        view = MainView(activity!!, null)
        view.delegate = this

        mAvailableCurrencyUnit = AvailableCurrency(activity as Context)

        return view
    }

    override fun onExchangeDataChanged(sell: BigMoney, toCurrency: CurrencyUnit) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}