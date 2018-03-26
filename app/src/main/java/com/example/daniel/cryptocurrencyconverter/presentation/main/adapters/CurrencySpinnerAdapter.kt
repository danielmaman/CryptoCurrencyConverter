package com.example.daniel.cryptocurrencyconverter.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.daniel.cryptocurrencyconverter.R
import com.example.daniel.cryptocurrencyconverter.R.id.currencySpinnerImageView
import com.example.daniel.cryptocurrencyconverter.R.id.currencySpinnerTextView
import com.example.daniel.cryptocurrencyconverter.presentation.models.AvailableCurrency
import com.google.gson.Gson

class CurrencySpinnerAdapter constructor(private val currencies: AvailableCurrency, val context: Context) : BaseAdapter() {
    var inflater : LayoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, view: View?, viewGroup: ViewGroup?): View {
        var view = view
        if (view == null) {// Recyclinam jau sukurtus view`us del memory leak`u, o naujus inflatin`am
            view = inflater.inflate(R.layout.currency_spinner_items, viewGroup, false)
        }

        val imageView = view?.findViewById<ImageView>(currencySpinnerImageView)
        val textView = view?.findViewById<TextView>(currencySpinnerTextView)

        imageView?.setImageResource(currencies.flags[position])
        textView?.text = currencies.availableCurrencies[position].code.toUpperCase()

        return view!!
    }

    override fun getItem(position: Int): String {
        return currencies.availableCurrencies[position].code
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return currencies.availableCurrencies.size
    }
    fun isCryptoCurrency(): Boolean{
        return currencies.cryptoCurrencies
    }
}