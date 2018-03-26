package com.example.daniel.cryptocurrencyconverter.presentation.main

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import com.example.daniel.cryptocurrencyconverter.R
import com.example.daniel.cryptocurrencyconverter.presentation.adapters.CurrencySpinnerAdapter
import com.example.daniel.cryptocurrencyconverter.presentation.main.adapters.BitcoinRatesRecyclerViewAdapter
import org.joda.money.BigMoney
import org.joda.money.CurrencyUnit

interface MainViewDelegate {
    fun onExchangeDataChanged(sell: BigMoney, toCurrency: CurrencyUnit)
}

class MainView(context: Context,attrs: AttributeSet?): ConstraintLayout(context,attrs){
     var delegate: MainViewDelegate?=null
     var view: View = LayoutInflater.from(context).inflate(R.layout.controller_main,this)

    fun attachSpinnersAdapter(sellAdapter: CurrencySpinnerAdapter, receiveAdapter: CurrencySpinnerAdapter){
        val sellSpinner = view.findViewById<Spinner>(R.id.sellSelectCurrencySpinner)
        val receiveSpinner = view.findViewById<Spinner>(R.id.receiveSelectCurrencySpinner)
        sellSpinner.adapter = sellAdapter
        receiveSpinner.adapter = receiveAdapter
    }

    fun attachRecyclerViewAdapter(adapter: BitcoinRatesRecyclerViewAdapter){
        val recyclerView = view.findViewById<RecyclerView>(R.id.ratesRecyclerView)
        val horizontalLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = horizontalLayoutManager
        recyclerView.adapter = adapter
    }

    fun attachListeners(){//TODO implement click listers
        val button = view.findViewById<Button>(R.id.convertButton)
        button.setOnClickListener{
            delegate?.onExchangeDataChanged( getSellMoney(), getReceiveCurrencyUnit())
        }

        val swapButton = view.findViewById<FloatingActionButton>(R.id.switchFloatingActionButton)
        swapButton.setOnClickListener {//TODO if bitcoin sell 0.xxxxx receive 0
            swapCurrencies()
        }
    }

    private fun swapCurrencies(){
        val sellSpinner = view.findViewById<Spinner>(R.id.sellSelectCurrencySpinner)
        val receiveSpinner = view.findViewById<Spinner>(R.id.receiveSelectCurrencySpinner)
        val tempAdapter = sellSpinner.adapter
        sellSpinner.adapter = receiveSpinner.adapter
        receiveSpinner.adapter = tempAdapter

        val sellEditView = view.findViewById<EditText>(R.id.sellAmountEditText)
        val receiveTextView = view.findViewById<TextView>(R.id.receiveAmountTextView)
        val tempSell = sellEditView.text
        sellEditView.setText(receiveTextView.text.toString())
        receiveTextView.text = tempSell
    }

    fun updateViewAfterRatesRefresh(chartName: String, timeUpdated: String ){
        val chartNameTextView = view.findViewById<TextView>(R.id.chartNameTextView)//TODO remove all view by id
        val updatedTextView = view.findViewById<TextView>(R.id.updatedTextView)

        chartNameTextView.text = chartName
        updatedTextView.text = timeUpdated

    }

    fun updateExchangeResultTextView(amount: String){
        val receiveAmountTextView = view.findViewById<TextView>(R.id.receiveAmountTextView)
        receiveAmountTextView.text = amount
    }

    private fun getSellMoney(): BigMoney{
        val sellSpinner = view.findViewById<Spinner>(R.id.sellSelectCurrencySpinner)
        val sellEditView = view.findViewById<EditText>(R.id.sellAmountEditText)
        val sellCurrency: String = sellSpinner.adapter.getItem(sellSpinner.selectedItemPosition).toString()
        return BigMoney.parse(sellCurrency +" "+ sellEditView.text )
    }

    private fun getReceiveCurrencyUnit(): CurrencyUnit{
        val receiveSpinner = view.findViewById<Spinner>(R.id.receiveSelectCurrencySpinner)
        val receiveCurrency: String = receiveSpinner.adapter.getItem(receiveSpinner.selectedItemPosition).toString()
        return CurrencyUnit.of(receiveCurrency)
    }
}