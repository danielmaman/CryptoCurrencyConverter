package com.example.daniel.cryptocurrencyconverter.base

import android.app.Application
import com.example.daniel.cryptocurrencyconverter.common.dagger.AppComponent
import com.example.daniel.cryptocurrencyconverter.common.dagger.AppModule
import com.example.daniel.cryptocurrencyconverter.common.dagger.DaggerAppComponent
import io.realm.Realm
import io.realm.RealmConfiguration
import timber.log.Timber


class BaseApplication : Application() {

    lateinit var mApplicationComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        Realm.init(this)
        val config = RealmConfiguration.Builder()
                .build()
        Realm.deleteRealm(config)
        Realm.setDefaultConfiguration(config)

        mApplicationComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()

        mApplicationComponent.inject(this)
    }


}