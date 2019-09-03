package rx.com.wyn.rxjavademo.demo;

import io.reactivex.Observable;

/**
 * Created by wangyn on 19/6/23.
 */

public class ResultHolder<T, R> {
    public Observable<T> observable;
    public R callback;
}
