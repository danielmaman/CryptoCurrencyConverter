package com.example.daniel.cryptocurrencyconverter

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.example.daniel.cryptocurrencyconverter.presentation.MainController
import kotlinx.android.synthetic.main.activity_main.*
import org.joda.money.CurrencyUnit

class MainActivity : AppCompatActivity() {

    lateinit var router : Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val list : List<String> = listOf("")
        CurrencyUnit.registerCurrency("BTC",-1,8,list)

        router = Conductor.attachRouter(this, container_main as ViewGroup, savedInstanceState)

        if (!router.hasRootController()) {
            router.setRoot(RouterTransaction.with(MainController()))
        }
    }
}
