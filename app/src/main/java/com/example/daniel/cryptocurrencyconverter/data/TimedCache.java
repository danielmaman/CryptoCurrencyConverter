package com.example.daniel.cryptocurrencyconverter.data;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.ReplaySubject;
import io.reactivex.subjects.Subject;


class TimedCache<T> {
    final Subject<T> cache = ReplaySubject.createWithTime(1, TimeUnit.MINUTES, Schedulers.trampoline() );

    final Single<T> valueProvider;

    TimedCache(Single<T> valueProvider) {
        this.valueProvider = valueProvider;
    }

    public Observable<T> valueObservable() {
        return cache.take(1)
                .switchIfEmpty(
                        valueProvider
                                .doOnSuccess(v -> {
                                    cache.onNext(v);
                                    // update realm here
                                })
                                .toObservable()
                );
    }
}