package com.example.daniel.cryptocurrencyconverter.presentation.main

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
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
import kotlinx.android.synthetic.main.controller_main.view.*
import org.joda.money.BigMoney
import org.joda.money.CurrencyUnit
import java.util.*

interface MainViewDelegate {
    fun onExchangeDataChanged(sell: BigMoney, toCurrency: CurrencyUnit)
}

class MainView(context: Context,attrs: AttributeSet?): ConstraintLayout(context,attrs){
     var delegate: MainViewDelegate?=null
     var view: View = LayoutInflater.from(context).inflate(R.layout.controller_main,this)

//
//var sellSpinner = view.findViewById<Spinner>(R.id.sellSelectCurrencySpinner)
    //    var receiveSpinner = view.findViewById<Spinner>(R.id.receiveSelectCurrencySpinner)
//    val sellEditView = view.findViewById<EditText>(R.id.sellAmountEditText)
//    val receiveTextView = view.findViewById<TextView>(R.id.receiveAmountTextView)
//    val recyclerView = view.findViewById<RecyclerView>(R.id.ratesRecyclerView)
//    val swapButton = view.findViewById<FloatingActionButton>(R.id.switchFloatingActionButton)
//    val convertButton = view.findViewById<Button>(R.id.convertButton)
//    val chartNameTextView = view.findViewById<TextView>(R.id.chartNameTextView)//TODO remove all view by id
//    val updatedTextView = view.findViewById<TextView>(R.id.updatedTextView)
    fun attachSpinnersAdapter(sellAdapter: CurrencySpinnerAdapter, receiveAdapter: CurrencySpinnerAdapter){
        sellSelectCurrencySpinner.adapter = sellAdapter
        receiveSelectCurrencySpinner.adapter = receiveAdapter
    }

    fun attachRecyclerViewAdapter(adapter: BitcoinRatesRecyclerViewAdapter){
        val horizontalLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        ratesRecyclerView.layoutManager = horizontalLayoutManager
        ratesRecyclerView.adapter = adapter
    }

    fun onSaveViewState(view: View, outState: Bundle) {
        outState.putString("sellAmount",    sellAmountEditText.text.toString())
        outState.putString("receiveAmount", receiveAmountTextView.text.toString())

        val isSellCrypto = (sellSelectCurrencySpinner.adapter as CurrencySpinnerAdapter).isCryptoCurrency()
        outState.putBoolean("isSellCrypto", isSellCrypto)

        val stringArrayList =  arrayListOf<String>()
        (ratesRecyclerView.adapter as BitcoinRatesRecyclerViewAdapter).mBitcoinRatesString.forEach {
            stringArrayList.add(it)
        }

        outState.putStringArrayList("recyclerViewRates",stringArrayList)
        outState.putString("lastUpdated", updatedTextView.text.toString())
        outState.putString("chartName", chartNameTextView.text.toString())
    }

    fun onRestoreViewState(view: View, savedViewState: Bundle) {
        sellAmountEditText.setText(savedViewState.getString("sellAmount"))
        receiveAmountTextView.text = savedViewState.getString("receiveAmount")
        updatedTextView.text= savedViewState.getString("lastUpdated")
        chartNameTextView.text = savedViewState.getString("chartName")
    }

    fun attachListeners(){//TODO implement click listers
        convertButton.setOnClickListener{
            delegate?.onExchangeDataChanged( getSellMoney(), getReceiveCurrencyUnit())
        }

        switchFloatingActionButton.setOnClickListener {
            swapCurrencies()
        }
    }

    private fun swapCurrencies(){

        val tempAdapter = sellSelectCurrencySpinner.adapter
        sellSelectCurrencySpinner.adapter = receiveSelectCurrencySpinner.adapter
        receiveSelectCurrencySpinner.adapter = tempAdapter

        val tempSell = sellAmountEditText.text
        sellAmountEditText.setText(receiveAmountTextView.text.toString())
        receiveAmountTextView.text = tempSell
    }

    fun updateViewAfterRatesRefresh(chartName: String, timeUpdated: String ){
        val updated = view.resources.getString(R.string.updated)

        chartNameTextView.text = chartName
        updatedTextView.text = updated +" "+ timeUpdated

    }

    fun updateExchangeResultTextView(amount: String){
        receiveAmountTextView.text = amount
    }

    private fun getSellMoney(): BigMoney{
        val sellCurrency: String = sellSelectCurrencySpinner.adapter.getItem(sellSelectCurrencySpinner.selectedItemPosition).toString()
        return BigMoney.parse(sellCurrency +" "+ sellAmountEditText.text )
    }

    private fun getReceiveCurrencyUnit(): CurrencyUnit{
        val receiveCurrency: String = receiveSelectCurrencySpinner.adapter.getItem(receiveSelectCurrencySpinner.selectedItemPosition).toString()
        return CurrencyUnit.of(receiveCurrency)
    }
}