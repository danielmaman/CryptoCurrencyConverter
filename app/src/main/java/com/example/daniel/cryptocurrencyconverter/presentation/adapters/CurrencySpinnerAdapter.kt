package com.example.daniel.cryptocurrencyconverter.presentation.adapters

/**
 * Created by Daniel on 3/20/2018.
 */
//class CurrencySpinnerAdapter constructor(currencies : AvailableCurrency, context: Context) : BaseAdapter() {//TODO
//
//    var inflater : LayoutInflater = LayoutInflater.from(context)
//
//    override fun getView(position: Int, view: View?, viewGroup: ViewGroup?): View {
//        if (view == null) {// Recyclinam jau sukurtus view`us del memory leak`u, o naujus inflatin`am
//            view = inflater.inflate(R.layout.currency_spinner_items, viewGroup, false)
//        }
//
//        val icon = view.findViewById(R.id.currencySpinnerImageView)
//        val names = view.findViewById(R.id.currencySpinnerTextView)
//
//        icon.setImageResource(currencies.getFlag(position))
//        names.setText(currencies.getAvailableCurrencies().get(position).getCode().toUpperCase())
//
//        return view
//    }
//
//    override fun getItem(position: Int): Any {
//        return currencies.getAvailableCurrencies().get(position).getCode()
//    }
//
//    override fun getItemId(position: Int): Long {
//        return 0
//    }
//
//    override fun getCount(): Int {
//        return currencies.getAvailableCurrencies().size()
//    }
//}