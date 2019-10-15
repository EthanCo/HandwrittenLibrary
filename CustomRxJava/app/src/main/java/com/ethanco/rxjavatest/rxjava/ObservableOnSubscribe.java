package com.ethanco.rxjavatest.rxjava;

/**
 * ObservableOnSubscribe
 *
 * @author EthanCo
 * @since 2019/10/5
 */
public interface ObservableOnSubscribe<T> {
    public void subscribe(Observer<? super T> observer); //? super T 是关键
}
