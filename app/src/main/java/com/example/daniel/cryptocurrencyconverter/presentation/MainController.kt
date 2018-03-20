package com.example.daniel.cryptocurrencyconverter.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.daniel.cryptocurrencyconverter.R
import com.example.daniel.cryptocurrencyconverter.base.BaseController

/**
 * Created by Daniel on 3/20/2018.
 */
class  MainController : BaseController(){
    override fun onCreateControllerView(inflater: LayoutInflater, container: ViewGroup): View {
        val view  = inflater.inflate(R.layout.controller_main, container, false)

        return view
    }
}