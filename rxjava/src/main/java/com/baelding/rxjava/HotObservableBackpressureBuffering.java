package com.baelding.rxjava;

import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

public class HotObservableBackpressureBuffering {
    public static void main(String[] args) throws InterruptedException {
        PublishSubject<Integer> source = PublishSubject.<Integer> create();

        source
          .buffer(1024)
          .observeOn(Schedulers.computation())
          .subscribe(ComputeFunction::compute, Throwable::printStackTrace);

        for (int i = 0; i < 1_000_000; i++) {
            source.onNext(i);
        }
        Thread.sleep(10_000);
    }
}
