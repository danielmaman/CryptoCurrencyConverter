package com.example.daniel.cryptocurrencyconverter.common.dagger

import com.example.daniel.cryptocurrencyconverter.data.BitcoinRateDraftMapper
import com.example.daniel.cryptocurrencyconverter.data.BitcoinRateRepository
import com.example.daniel.cryptocurrencyconverter.data.api.BitcoinExchangeApiClient
import com.example.daniel.cryptocurrencyconverter.data.api.BitcoinRateService
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApiModule {

    @Singleton
    @Provides
    fun providesRetrofit(): BitcoinRateService {
        val EXCHANGE_BASE_URL = "https://api.coindesk.com/"
        return Retrofit.Builder()
                .baseUrl(EXCHANGE_BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create<BitcoinRateService>(BitcoinRateService::class.java)
    }

    fun provideApiClient(bitcoinRateService: BitcoinRateService): BitcoinExchangeApiClient {
        return BitcoinExchangeApiClient(bitcoinRateService)
    }

    @Singleton
    @Provides
    fun providesDraftMapper(): BitcoinRateDraftMapper{
        return BitcoinRateDraftMapper()
    }

    @Provides
    @Singleton
    fun providesRepository(apiClient : BitcoinExchangeApiClient,draftMapper : BitcoinRateDraftMapper, compositeDisposable: CompositeDisposable): BitcoinRateRepository {
        return BitcoinRateRepository(apiClient,draftMapper,compositeDisposable)
    }
}