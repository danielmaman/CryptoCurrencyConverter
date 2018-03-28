package com.example.daniel.cryptocurrencyconverter.common.dagger

import com.example.daniel.cryptocurrencyconverter.presentation.MainActivity
import com.example.daniel.cryptocurrencyconverter.base.BaseApplication
import com.example.daniel.cryptocurrencyconverter.data.CryptoRateDbRepository
import com.example.daniel.cryptocurrencyconverter.domain.RepositoryInteractor
import com.example.daniel.cryptocurrencyconverter.presentation.main.MainController
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppModule::class), (ApiModule::class)])
interface AppComponent{
    fun inject(baseApplication: BaseApplication)
    fun inject(mainActivity: MainActivity)
    fun inject(cryptoRateDbRepository: CryptoRateDbRepository)
    fun inject(mainController: MainController)
    fun inject(repositoryInteractor: RepositoryInteractor)
}