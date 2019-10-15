package com.ethanco.rxjavatest.rxjava;

/**
 * 观察者 下游
 *
 * @author EthanCo
 * @since 2019/10/5
 */
public interface Observer<T> {

    void onSubscribe();

    void onNext(T t);

    void onError(Throwable e);

    void onComplete();
}
