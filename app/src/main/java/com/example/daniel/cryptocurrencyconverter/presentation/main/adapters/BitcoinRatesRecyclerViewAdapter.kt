package com.example.daniel.cryptocurrencyconverter.presentation.main.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.daniel.cryptocurrencyconverter.R
import com.example.daniel.cryptocurrencyconverter.presentation.models.AvailableCurrency
import kotlinx.android.synthetic.main.balance_recyclerview_items.view.*
import org.joda.money.Money

/**
 * Created by Daniel on 3/23/2018.
 */
class BitcoinRatesRecyclerViewAdapter constructor(bitcoinRates: MutableList<Money>, availableCurrency: AvailableCurrency)
                                        :RecyclerView.Adapter<BitcoinRatesRecyclerViewAdapter.BitcoinRatesViewHolder>(){
    val mBitcoinRates = bitcoinRates
    private val mAvailableCurrency = availableCurrency

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BitcoinRatesViewHolder {
        val balanceView = LayoutInflater.from(parent.context).inflate(R.layout.balance_recyclerview_items, parent, false)
        return BitcoinRatesViewHolder(balanceView)
    }

    override fun getItemCount(): Int {
        return mAvailableCurrency.availableCurrencies.size
    }

    override fun onBindViewHolder(holder: BitcoinRatesViewHolder, position: Int) {
        holder.textView.setText(mBitcoinRates[position].amount.toString() +mBitcoinRates[position].currencyUnit.code.toString())//TODO different type not Money
        holder.imageView.setImageResource(mAvailableCurrency.flags[position])
    }

    class BitcoinRatesViewHolder constructor(view: View) : RecyclerView.ViewHolder(view) {
        var imageView: ImageView = view.findViewById(R.id.balanceImageView)//TODO rename
        var textView: TextView = view.findViewById(R.id.balanceTextView)

    }
}