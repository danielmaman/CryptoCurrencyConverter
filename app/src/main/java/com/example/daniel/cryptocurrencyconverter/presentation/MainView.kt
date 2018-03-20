package com.example.daniel.cryptocurrencyconverter.presentation

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.example.daniel.cryptocurrencyconverter.R
import org.joda.money.BigMoney
import org.joda.money.CurrencyUnit

interface MainViewDelegate {
   // fun onConvertButtonTapped()
    fun onExchangeDataChanged(sell: BigMoney, toCurrency: CurrencyUnit)
}
class MainView(context: Context,attrs: AttributeSet?): ConstraintLayout(context,attrs){
    lateinit var delegate: MainViewDelegate

  init {
      LayoutInflater.from(context).inflate(R.layout.controller_main,this)
  }

}