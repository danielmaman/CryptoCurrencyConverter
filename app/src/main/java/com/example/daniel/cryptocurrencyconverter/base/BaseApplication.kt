package com.example.daniel.cryptocurrencyconverter.base

import android.app.Application
import android.content.Context
import android.support.annotation.CallSuper
import org.joda.money.CurrencyUnit
import timber.log.Timber


class BaseApplication : Application() {
    companion object {
//        lateinit var mApplicationComponent

        fun get(context : Context) : BaseApplication {
        return context.applicationContext as BaseApplication
        }

//        fun getApplicationComponent(): ApplicationComponent {TODO
//            return mApplicationComponent
//        }
    }

    @CallSuper
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.asTree())
    }


}