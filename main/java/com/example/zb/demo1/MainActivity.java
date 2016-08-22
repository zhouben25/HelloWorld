package com.example.zb.demo1;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.util.Log;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends PreferenceActivity {

    private String tag = "zbbbb";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.layout);
        test();
    }

    private void test() {
        Observable.merge(myObservable1, myObservable2).subscribe(subscriber);
    }

    Observable<String> myObservable1 = Observable.create(
            new Observable.OnSubscribe<String>() {
                @Override
                public void call(Subscriber<? super String> sub) {
                    Log.d(tag, "                   call 1 begin");
                    sub.onNext("Hello, world 1 begin!");
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.d(tag, "                   call 1 end");
                    sub.onNext("Hello, world 1 end!");
                    sub.onCompleted();
                }
            }
    ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    Observable<String> myObservable2 = Observable.create(
            new Observable.OnSubscribe<String>() {
                @Override
                public void call(Subscriber<? super String> sub) {
                    Log.d(tag, "                   call 1 begin");
                    sub.onNext("Hello, world 2 begin!");
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.d(tag, "                   call 1 end");
                    sub.onNext("Hello, world 2 end!");
                    sub.onCompleted();
                }
            }
    ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

    Observer<String> observer = new Observer<String>() {
        @Override
        public void onNext(String s) {
            Log.d(tag, "Item: " + s);
        }

        @Override
        public void onCompleted() {
            Log.d(tag, "Completed!");
        }

        @Override
        public void onError(Throwable e) {
            Log.d(tag, "Error!");
        }
    };

    Subscriber<String> subscriber = new Subscriber<String>() {
        @Override
        public void onNext(String s) {
            Log.d(tag, "Item: " + s);
        }

        @Override
        public void onCompleted() {
            Log.d(tag, "Completed!");
        }

        @Override
        public void onError(Throwable e) {
            Log.d(tag, "Error!");
        }
    };
}
