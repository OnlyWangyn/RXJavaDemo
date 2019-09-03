package rx.com.wyn.rxjavademo.demo;

import io.reactivex.Observer;

/**
 * Created by wangyn on 19/6/23.
 */

public class StringCallbackAdapter<T> implements TypeAdapter<T> {
    @SuppressWarnings("unchecked")
    @Override
    public <R> R getCallback(Converter<T> converter, Observer<T> observer) {
        return (R) new StringCallbackWrapped<>(converter, observer);
    }
}
