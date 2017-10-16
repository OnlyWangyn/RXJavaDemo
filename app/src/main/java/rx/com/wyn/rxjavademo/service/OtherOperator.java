package rx.com.wyn.rxjavademo.service;

import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;

/**
 * Created by nancy on 17-10-15.
 */

public class OtherOperator {
    private static String TAG = OtherOperator.class.getSimpleName();
    public static void operatorOne(){
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override public void subscribe(ObservableEmitter emitter) throws Exception {
                Log.d(TAG, "emit 1");
                emitter.onNext(1);
                Log.d(TAG, "emit 2");
                emitter.onNext(2);
                Log.d(TAG, "emit 3");
                emitter.onNext(3);
                Log.d(TAG, "emit 4");
                emitter.onNext(4);
                Log.d(TAG, "emit complete");
                emitter.onComplete();
               }
         }).subscribe(new Observer<Integer>() {
                    private Disposable mDisposable;
                    @Override public void onSubscribe(Disposable d) {
                        mDisposable = d;
                        Log.d(TAG, "subscribe"); }
                    @Override public void onNext(Integer value) {
                        Log.d(TAG, "receive " + value);
                        if(value == 3){
                            mDisposable.dispose();
                        }
                    }
                    @Override public void onError(Throwable e) { Log.d(TAG, "error"); }
                    @Override public void onComplete() { Log.d(TAG, "complete"); } });

    }
    public static void operatorTwo(){
        Observable<Integer> observable1 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                Log.d(TAG, "emit 1"); emitter.onNext(1); Log.d(TAG, "emit 2");
                emitter.onNext(2);
                Log.d(TAG, "emit 3");
                emitter.onNext(3);
                Log.d(TAG, "emit 4");
                emitter.onNext(4);
                Log.d(TAG, "emit complete1");
                emitter.onComplete(); } });
        Observable<String> observable2 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                Log.d(TAG, "emit A");
                emitter.onNext("A");
                Log.d(TAG, "emit B");
                emitter.onNext("B");
                Log.d(TAG, "emit C");
                emitter.onNext("C");
                Log.d(TAG, "emit complete2");
                emitter.onComplete(); } });

        Observable.zip(observable1, observable2, new BiFunction<Integer, String, String>() {
            @Override public String apply(Integer integer, String s) throws Exception {
                return integer + s; } })
                .subscribe(new Observer<String>() {
                    @Override public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe"); }
                    @Override public void onNext(String value) {
                        Log.d(TAG, "onNext: " + value); }
                    @Override public void onError(Throwable e) {
                        Log.d(TAG, "onError"); }
                    @Override public void onComplete() {
                        Log.d(TAG, "onComplete"); } });

    }
}
