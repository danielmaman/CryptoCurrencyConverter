package com.example.daniel.cryptocurrencyconverter.presentation

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Spinner
import com.example.daniel.cryptocurrencyconverter.MainActivity
import com.example.daniel.cryptocurrencyconverter.R
import com.example.daniel.cryptocurrencyconverter.base.BaseApplication
import com.example.daniel.cryptocurrencyconverter.presentation.adapters.CurrencySpinnerAdapter
import com.example.daniel.cryptocurrencyconverter.presentation.models.AvailableCurrency
import org.joda.money.BigMoney
import org.joda.money.CurrencyUnit
import javax.inject.Inject

interface MainViewDelegate {
    fun onExchangeDataChanged(sell: BigMoney, toCurrency: CurrencyUnit)
}

class MainView(context: Context,attrs: AttributeSet?): ConstraintLayout(context,attrs){
     var delegate: MainViewDelegate?=null
     var view: View = LayoutInflater.from(context).inflate(R.layout.controller_main,this)

    fun attachSpinnersAdapter(adapter: CurrencySpinnerAdapter){
        val sellSpinner = view.findViewById<Spinner>(R.id.sellSelectCurrencySpinner)
        val buySpinner = view.findViewById<Spinner>(R.id.buySelectCurrencySpinner)
        sellSpinner?.adapter = adapter
        buySpinner?.adapter = adapter
    }

}