package com.example.daniel.cryptocurrencyconverter.presentation.main

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.Spinner
import com.example.daniel.cryptocurrencyconverter.R
import com.example.daniel.cryptocurrencyconverter.presentation.adapters.CurrencySpinnerAdapter
import com.example.daniel.cryptocurrencyconverter.presentation.main.adapters.BitcoinRatesRecyclerViewAdapter
import org.joda.money.BigMoney
import org.joda.money.CurrencyUnit

interface MainViewDelegate {
    fun onExchangeDataChanged(sell: BigMoney, toCurrency: CurrencyUnit)
    fun onRefreshTapped()
}

class MainView(context: Context,attrs: AttributeSet?): ConstraintLayout(context,attrs){
     var delegate: MainViewDelegate?=null
     var view: View = LayoutInflater.from(context).inflate(R.layout.controller_main,this)

    fun attachSpinnersAdapter(adapter: CurrencySpinnerAdapter){
        val sellSpinner = view.findViewById<Spinner>(R.id.sellSelectCurrencySpinner)
        val buySpinner = view.findViewById<Spinner>(R.id.buySelectCurrencySpinner)
        sellSpinner.adapter = adapter
        buySpinner.adapter = adapter
    }

    fun attachRecyclerViewAdapter(adapter: BitcoinRatesRecyclerViewAdapter){
        val recyclerView = view.findViewById<RecyclerView>(R.id.balanceRecyclerView)//TODO rename
        val horizontalLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = horizontalLayoutManager
        recyclerView.adapter = adapter
    }

    fun attachListeners(){
        var button = view.findViewById<Button>(R.id.convertButton)
        button.setOnClickListener{
            delegate?.onRefreshTapped()
        }
    }
}