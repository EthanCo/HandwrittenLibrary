package com.ethanco.rxjavatest.rxjava;

/**
 * 被观察者 上游
 *
 * @author EthanCo
 * @since 2019/10/5
 */
public class Observable<T> {
    private final ObservableOnSubscribe<T> source;

    public Observable(ObservableOnSubscribe source) {
        this.source = source;
    }

    public static <T> Observable<T> create(ObservableOnSubscribe<? extends T> source){
        return new Observable<>(source);
    }

    // 单一
    public static <T> Observable<T> just(final T t) {
        return new Observable<T>(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(Observer<? super T> observableEmitter) {
                // 发射用户传递的参数数据 去发射事件
                observableEmitter.onNext(t);

                // 调用完毕
                observableEmitter.onComplete(); // 发射完毕事件
            }
        });
    }

    // 2个参数
    public static <T> Observable<T> just(final T t, final T t2) {
        return new Observable<T>(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(Observer<? super T> observableEmitter) {
                // 发射用户传递的参数数据 去发射事件
                observableEmitter.onNext(t);
                observableEmitter.onNext(t2);

                // 调用完毕
                observableEmitter.onComplete(); // 发射完毕事件
            }
        });
    }

    // 3个参数
    public static <T> Observable<T> just(final T t, final T t2, final T t3) {
        return new Observable<T>(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(Observer<? super T> observableEmitter) {
                // 发射用户传递的参数数据 去发射事件
                observableEmitter.onNext(t);
                observableEmitter.onNext(t2);
                observableEmitter.onNext(t3);

                // 调用完毕
                observableEmitter.onComplete(); // 发射完毕事件
            }
        });
    }

    // 1 2 3 4 可变参数
    public static <T> Observable<T> just(final T... t) { // just 内部发射了
        // 想办法让 source 是不为null的，  而我们的create操作符是，使用者自己传递进来的
        return new Observable<T>(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(Observer<? super T> observableEmitter) { // observableEmitter == Observer
                for (T t1 : t) {

                    // 发射用户传递的参数数据 去发射事件
                    observableEmitter.onNext(t1);
                }

                // 调用完毕
                observableEmitter.onComplete(); // 发射完毕事件
            }
        });
    }

    // fromArray
    public static <T> Observable<T> fromArray(final T[] ts) {
        return new Observable<T>(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(Observer<? super T> observableEmitter) {
                // 根据使用者 传递的参数 分发事件下去
                for (T t : ts) {
                    observableEmitter.onNext(t);
                }
                // 分发完毕的事件
                observableEmitter.onComplete();
            }
        });
    }

    // fromArray
    public static <T> Observable<T> fromArray(final T[] ts, final T[] ts2) {
        return new Observable<T>(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(Observer<? super T> observableEmitter) {
                // 根据使用者 传递的参数 分发事件下去
                for (T t : ts) {
                    observableEmitter.onNext(t);
                }
                // 根据使用者 传递的参数 分发事件下去
                for (T t : ts2) {
                    observableEmitter.onNext(t);
                }
                // 分发完毕的事件
                observableEmitter.onComplete();
            }
        });
    }

    /**
     * map变换操作符
     *
     * T == 上一层传递过来的类型  Integer  变换前的类型
     * R == 给一层的类型         String   变换后的类型
     *
     */
    public <R> Observable<R> map(Function<? super T, ? extends R> function) { // ? super T 可写模式

        ObservableMap<T, R> observableMap = new ObservableMap(source, function); // source 上一层的能力

        return new Observable<R>(observableMap); // source  该怎么来？     observableMap是source的实现类
    }

    // todo 给所有上游分配异步线程
    public Observable<T> observables_On() {
        // 实例化 处理上游的线程操作符
        return create(new ObservableOnIO(source));
    }

    // todo 给下游分配Android主线程
    public Observable<T> observers_AndroidMain_On() {
        // 实例化 处理下游的线程操作符
        ObserverAndroidMain_On<T> observerAndroidMain_on = new ObserverAndroidMain_On(source);
        return create(observerAndroidMain_on);
    }

    public void subscribe(Observer<? super T> observer){
        observer.onSubscribe();

        source.subscribe(observer);
    }
}
