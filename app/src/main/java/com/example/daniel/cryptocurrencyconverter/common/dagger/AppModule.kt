package com.example.daniel.cryptocurrencyconverter.common.dagger

import com.example.daniel.cryptocurrencyconverter.base.BaseApplication
import com.example.daniel.cryptocurrencyconverter.data.CryptoRateDbRepository
import com.example.daniel.cryptocurrencyconverter.domain.RepositoryInteractor
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

@Singleton
@Module
class AppModule {
    var mBaseApplication: BaseApplication

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
    fun providesCompositeDisposable(): CompositeDisposable{
        return CompositeDisposable()
    }

    @Provides
    @Singleton
    fun providesInteractor(baseApplication: BaseApplication): RepositoryInteractor{
        return RepositoryInteractor(baseApplication)
    }

    @Provides
    @Singleton
    fun providesDbRepository(): CryptoRateDbRepository {
        return CryptoRateDbRepository()
    }
}