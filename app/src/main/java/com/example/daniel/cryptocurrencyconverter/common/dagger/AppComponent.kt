package com.example.daniel.cryptocurrencyconverter.common.dagger

import com.example.daniel.cryptocurrencyconverter.presentation.MainActivity
import com.example.daniel.cryptocurrencyconverter.base.BaseApplication
import com.example.daniel.cryptocurrencyconverter.data.BitcoinRateRepository
import com.example.daniel.cryptocurrencyconverter.presentation.main.MainController
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class, ApiModule::class))
interface AppComponent{
    fun inject(baseApplication: BaseApplication)
    fun inject(mainActivity: MainActivity)
    fun inject(bitcoinRateRepository: BitcoinRateRepository)
    fun inject(mainController: MainController)
}