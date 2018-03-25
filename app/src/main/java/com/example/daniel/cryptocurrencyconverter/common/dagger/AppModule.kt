package com.example.daniel.cryptocurrencyconverter.common.dagger

import com.example.daniel.cryptocurrencyconverter.base.BaseApplication
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

@Module
class AppModule {
    lateinit var mBaseApplication: BaseApplication

    constructor(baseApplication: BaseApplication){
        mBaseApplication = baseApplication
    }

    @Provides
    @Singleton
    fun provideApplication(): BaseApplication{
        return mBaseApplication
    }

    @Provides
    @Singleton
    fun providesCompositeDisposible(): CompositeDisposable{
        return CompositeDisposable()
    }

}