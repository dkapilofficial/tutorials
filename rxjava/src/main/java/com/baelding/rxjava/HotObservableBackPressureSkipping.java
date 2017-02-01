package com.baelding.rxjava;

import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

import java.util.concurrent.TimeUnit;

public class HotObservableBackPressureSkipping {
    public static void main(String[] args) throws InterruptedException {
        PublishSubject<Integer> source = PublishSubject.<Integer>create();

        //buffer
        source
//              .debounce(1, TimeUnit.SECONDS)
//              .sample(1, TimeUnit.SECONDS)
//              .throttleFirst(100, TimeUnit.MILLISECONDS)
                .throttleLast(100, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.computation())
                .subscribe(ComputeFunction::compute, Throwable::printStackTrace);


        for (int i = 0; i < 1_000_000; i++) {
            source.onNext(i);
        }
        Thread.sleep(10_000);
    }
}
