package com.example.daniel.cryptocurrencyconverter.presentation.main

import android.content.Context
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.LinearLayoutManager
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import com.example.daniel.cryptocurrencyconverter.R
import com.example.daniel.cryptocurrencyconverter.presentation.main.adapters.CurrencySpinnerAdapter
import com.example.daniel.cryptocurrencyconverter.presentation.main.adapters.CryptoRatesRecyclerViewAdapter
import kotlinx.android.synthetic.main.controller_main.view.*
import org.joda.money.BigMoney
import org.joda.money.CurrencyUnit

interface MainViewDelegate {
    fun onExchangeDataChanged(sell: BigMoney, toCurrency: CurrencyUnit)
}

class MainView(context: Context,attrs: AttributeSet?): ConstraintLayout(context,attrs){
    var delegate: MainViewDelegate?=null
    var view: View = LayoutInflater.from(context).inflate(R.layout.controller_main,this)

    val SELL_AMOUNT_KEY ="sellAmount"
    val RECEIVE_AMOUNT_KEY ="receiveAmount"
    val SELL_CRYPTO_BOOLEAN_KEY ="isSellCrypto"
    val RECYCLERVIEW_RATE_KEY ="recyclerViewRates"
    val LAST_UPDATED_KEY = "lastUpdated"
    val CHART_NAME_KEY = "chartName"

    fun attachSpinnersAdapter(sellAdapter: CurrencySpinnerAdapter, receiveAdapter: CurrencySpinnerAdapter){
        sellSelectCurrencySpinner.adapter = sellAdapter
        receiveSelectCurrencySpinner.adapter = receiveAdapter
    }

    fun attachRecyclerViewAdapter(adapter: CryptoRatesRecyclerViewAdapter){
        val horizontalLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        ratesRecyclerView.layoutManager = horizontalLayoutManager
        ratesRecyclerView.adapter = adapter
    }

    fun onSaveViewState(view: View, outState: Bundle) {
        outState.putString(SELL_AMOUNT_KEY,    sellAmountEditText.text.toString())
        outState.putString(RECEIVE_AMOUNT_KEY, receiveAmountTextView.text.toString())

        val isSellCrypto = (sellSelectCurrencySpinner.adapter as CurrencySpinnerAdapter).isCryptoCurrency()
        outState.putBoolean(SELL_CRYPTO_BOOLEAN_KEY, isSellCrypto)

        val stringArrayList =  arrayListOf<String>()
        (ratesRecyclerView.adapter as CryptoRatesRecyclerViewAdapter).mCryptoRatesString.forEach {
            stringArrayList.add(it)
        }

        outState.putStringArrayList(RECYCLERVIEW_RATE_KEY,stringArrayList)
        outState.putString(LAST_UPDATED_KEY, updatedTextView.text.toString())
        outState.putString(CHART_NAME_KEY, chartNameTextView.text.toString())
    }

    fun onRestoreViewState(view: View, savedViewState: Bundle) {
        sellAmountEditText.setText(savedViewState.getString(SELL_AMOUNT_KEY))
        receiveAmountTextView.text = savedViewState.getString(RECEIVE_AMOUNT_KEY)
        updatedTextView.text= savedViewState.getString(LAST_UPDATED_KEY)
        chartNameTextView.text = savedViewState.getString(CHART_NAME_KEY)
    }

    fun attachListeners(){
        convertButton.setOnClickListener{
            if (sellAmountEditText.text.toString() != ""){
                delegate?.onExchangeDataChanged( getSellMoney(), getReceiveCurrencyUnit())
            }
        }

        switchFloatingActionButton.setOnClickListener {
            swapCurrencies()
        }
    }

    private fun swapCurrencies(){
        val sellSpinnerIndex = sellSelectCurrencySpinner.selectedItemPosition
        val receiveSpinnerIndex = receiveSelectCurrencySpinner.selectedItemPosition

        val tempAdapter = sellSelectCurrencySpinner.adapter
        sellSelectCurrencySpinner.adapter = receiveSelectCurrencySpinner.adapter
        receiveSelectCurrencySpinner.adapter = tempAdapter

        receiveSelectCurrencySpinner.setSelection(sellSpinnerIndex)
        sellSelectCurrencySpinner.setSelection(receiveSpinnerIndex)

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
        return BigMoney.parse(sellCurrency +" "+ getFormattedSellAmount() )
    }

    private fun getFormattedSellAmount():String{
      return  sellAmountEditText.text.toString().replace(",","")
    }

    private fun getReceiveCurrencyUnit(): CurrencyUnit{
        val receiveCurrency: String = receiveSelectCurrencySpinner.adapter.getItem(receiveSelectCurrencySpinner.selectedItemPosition).toString()
        return CurrencyUnit.of(receiveCurrency)
    }
}