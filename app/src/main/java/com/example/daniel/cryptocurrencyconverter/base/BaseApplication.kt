package com.example.daniel.cryptocurrencyconverter.base

import android.app.Application
import com.example.daniel.cryptocurrencyconverter.common.dagger.AppComponent
import com.example.daniel.cryptocurrencyconverter.common.dagger.AppModule
import com.example.daniel.cryptocurrencyconverter.common.dagger.DaggerAppComponent
import timber.log.Timber


class BaseApplication : Application() {

    lateinit var mApplicationComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        mApplicationComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()

        mApplicationComponent.inject(this)
    }


}