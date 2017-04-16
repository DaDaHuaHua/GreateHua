package com.example.song.greathua;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by PVer on 2017/4/16.
 * 测试操作符
 */

public class RxJavaTest03操作符 {

    private Observable<Integer> makeObservable() {
        return Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
            }
        });
    }

    /**
     * Map
     */
    @Test
    public void TestMap() {
        makeObservable().map(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Exception {
                return "map() Integer to  String  " + integer;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println("received String : " + s);
            }
        });
    }


    /**
     * FlatMap 无序发射
     * ConcatMap 有序发射
     */
    @Test
    public void TestFlatMap() {
        makeObservable()
                //.flatMap(new Function<Integer, ObservableSource<String>>() {
                .concatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                List<String> list = new ArrayList<String>();
                for (int i = 0; i < 3; i++) {
                    list.add("List value " + integer);
                }
                return Observable.fromIterable(list)
                       // .delay(10, TimeUnit.MILLISECONDS)
                        ;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println(s);
            }
        });
    }

    /**
     * 测试ZIP
     */
    @Test
    public void TestZip(){
        Observable<Integer> observable1 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                System.out.println("emit 1");
                e.onNext(1);
                Thread.sleep(1000);

                System.out.println("emit 2");
                e.onNext(2);
                Thread.sleep(1000);

                System.out.println("emit 3");
                e.onNext(3);
                Thread.sleep(1000);

                System.out.println("emit 4");
                e.onNext(4);
                Thread.sleep(1000);

                System.out.println("emit onComplete1");
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io());

        Observable<String> observable2 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                System.out.println("emit A");
                e.onNext("A");
                Thread.sleep(1000);

                System.out.println("emit B");
                e.onNext("B");
                Thread.sleep(1000);

                System.out.println("emit C");
                e.onNext("C");
                Thread.sleep(1000);

                System.out.println("emit onComplete2");
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io());

        Observable.zip(observable1, observable2, new BiFunction<Integer, String, String>() {
            @Override
            public String apply(Integer integer, String s) throws Exception {
                return integer + s;
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("onSubscribe");
            }

            @Override
            public void onNext(String s) {
                System.out.println("onNext: "+s);
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError : "+ e.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        });
    }

    /***
     *  测试BackPressure
     */
    @Test
    public void TestBackPressure1(){
    }
}
