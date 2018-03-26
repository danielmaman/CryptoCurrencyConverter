package com.example.daniel.cryptocurrencyconverter.presentation.main.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.daniel.cryptocurrencyconverter.R
import com.example.daniel.cryptocurrencyconverter.presentation.models.AvailableCurrency
import com.example.daniel.cryptocurrencyconverter.presentation.main.models.DisplayableCurrency

class CryptoRatesRecyclerViewAdapter constructor(availableCurrency: AvailableCurrency, cryptoRates: MutableList<DisplayableCurrency> = mutableListOf())
                                        :RecyclerView.Adapter<CryptoRatesRecyclerViewAdapter.CryptoRatesViewHolder>(){
    val mCryptoRates = cryptoRates
    private val mAvailableCurrency = availableCurrency
    var mCryptoRatesString: ArrayList<String> = arrayListOf()

    constructor(availableCurrency: AvailableCurrency, cryptoRatesString: ArrayList<String>) : this(availableCurrency){
        mCryptoRatesString = cryptoRatesString
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoRatesViewHolder {
        val balanceView = LayoutInflater.from(parent.context).inflate(R.layout.balance_recyclerview_items, parent, false)
        return CryptoRatesViewHolder(balanceView)
    }

    override fun getItemCount(): Int {
        return mAvailableCurrency.availableCurrencies.size
    }

    override fun onBindViewHolder(holder: CryptoRatesViewHolder, position: Int) {
        if (mCryptoRates.isNotEmpty()){
            mCryptoRatesString.add(mCryptoRates[position].toString())
            holder.textView.text = mCryptoRates[position].toString()
            holder.imageView.setImageResource(mAvailableCurrency.flags[position])
        }else{
            holder.textView.text = mCryptoRatesString[position]
            holder.imageView.setImageResource(mAvailableCurrency.flags[position])
        }
    }

    class CryptoRatesViewHolder constructor(view: View) : RecyclerView.ViewHolder(view) {
        var imageView: ImageView = view.findViewById(R.id.ratesImageView)
        var textView: TextView = view.findViewById(R.id.ratesTextView)

    }
}