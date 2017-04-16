package com.example.song.greathua;

import org.junit.Test;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by PVer on 2017/4/15.
 * RxJava用法
 */

public class RxJavaTest01基本用法 {

    private Observable<Integer> makeObservable() {
        return Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> observableEmitter) throws Exception {
                System.out.println("emmit 1");
                observableEmitter.onNext(1);
                System.out.println("emmit 2");
                observableEmitter.onNext(2);
                System.out.println("emmit 3");
                observableEmitter.onNext(3);
                System.out.println("emmit 4");
                observableEmitter.onNext(4);
                observableEmitter.onComplete();
                // observableEmitter.onError(new Throwable("抛出了error"));
            }
        });
    }

    public Observer<Integer> makeObserver() {
        return new Observer<Integer>() {
            private Disposable mDisposable;
            int i;

            @Override
            public void onSubscribe(Disposable disposable) {
                System.out.println("subscribe");
                mDisposable = disposable;
            }

            @Override
            public void onNext(Integer integer) {
                System.out.println("observer onNext()" + integer);
                i++;
                if (i == 2) {
                    System.out.println("dispose");
                    mDisposable.dispose();
                    System.out.println("isDisposed == " + mDisposable.isDisposed());
                }
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("observer onError and msg =" + throwable.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("observer onComplete");
            }
        };
    }

    //基本用法
    @Test
    public void simple1() {
        //创建Observable
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> observableEmitter) throws Exception {
                System.out.println("emmit 1");
                observableEmitter.onNext(1);
                System.out.println("emmit 2");
                observableEmitter.onNext(2);
                System.out.println("emmit 3");
                observableEmitter.onNext(3);
                System.out.println("emmit 4");
                observableEmitter.onNext(4);
                observableEmitter.onComplete();
                // observableEmitter.onError(new Throwable("抛出了error"));
            }
        });
        //创建Observer
        Observer<Integer> observer = new Observer<Integer>() {
            private Disposable mDisposable;
            int i;

            @Override
            public void onSubscribe(Disposable disposable) {
                System.out.println("subscribe");
                mDisposable = disposable;
            }

            @Override
            public void onNext(Integer integer) {
                System.out.println("observer onNext()" + integer);
                i++;
                if (i == 2) {
                    System.out.println("dispose");
                    mDisposable.dispose();
                    System.out.println("isDisposed == " + mDisposable.isDisposed());
                }
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("observer onError and msg =" + throwable.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("observer onComplete");
            }
        };
        //建立连接
        observable.subscribe(observer);
    }


    /**
     * 测试subscribe重载方法
     */
    @Test
    public void TestSubscribe() {
        Observable<Integer> observable = makeObservable();

        //一个Consumer参数： 只关心onNext事件
//        observable.subscribe(new Consumer<Integer>() {
//            @Override
//            public void accept(Integer integer) throws Exception {
//                System.out.println(integer);
//            }
//        });
        //---------------------------------------------------------------

        //两个Consumer参数： onNext事件 + onError事件
//        observable.subscribe(new Consumer<Integer>() {
//            @Override
//            public void accept(Integer integer) throws Exception {
//                System.out.println(integer);
//            }
//        }, new Consumer<Throwable>() {
//            @Override
//            public void accept(Throwable throwable) throws Exception {
//                System.out.println(throwable.getMessage());
//            }
//        });

        //-----------------------------------------------------------------

        //三个Consumer参数：onNext + onError + onComplete
//        observable.subscribe(new Consumer<Integer>() {
//            @Override
//            public void accept(Integer integer) throws Exception {
//                System.out.println(integer);
//            }
//        }, new Consumer<Throwable>() {
//            @Override
//            public void accept(Throwable throwable) throws Exception {
//                System.out.println(throwable.getMessage());
//            }
//        }, new Action() {
//            @Override
//            public void run() throws Exception {
//                System.out.println("complete");
//            }
//        });

        //---------------------------------------------------------------------
        //四个Consumer参数：onNext + onError + onComplete +onSubscribe
        observable.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                System.out.println(integer);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                System.out.println(throwable.getMessage());
            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                System.out.println("complete");
            }
        }, new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                System.out.println("onSubscribe");
            }
        });
    }
}
