package com.example.daniel.cryptocurrencyconverter.base

import android.app.Application
import com.example.daniel.cryptocurrencyconverter.common.dagger.AppComponent
import com.example.daniel.cryptocurrencyconverter.common.dagger.AppModule
import com.example.daniel.cryptocurrencyconverter.common.dagger.DaggerAppComponent
import io.realm.Realm
import io.realm.RealmConfiguration
import org.joda.money.CurrencyUnit
import timber.log.Timber


class BaseApplication : Application() {

    lateinit var mApplicationComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        Realm.init(this)
        val config = RealmConfiguration.Builder()
                .build()
       // Realm.deleteRealm(config)//TODO remove and make override instace every time
        Realm.setDefaultConfiguration(config)

        val list : List<String> = listOf("")
        CurrencyUnit.registerCurrency("BTC",-1,8,list)

        mApplicationComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()

        mApplicationComponent.inject(this)
    }

}