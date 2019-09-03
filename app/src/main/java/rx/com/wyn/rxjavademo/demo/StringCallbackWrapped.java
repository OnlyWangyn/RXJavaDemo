package rx.com.wyn.rxjavademo.demo;

import io.reactivex.Observer;

/**
 * Created by wangyn on 19/6/23.
 */

public class StringCallbackWrapped<T> implements StringCallback {
    private final Converter<T> mConverter;
    private final Observer<T> mObserver;

    StringCallbackWrapped(Converter<T> converter, Observer<T> observer){
        mConverter = converter;
        mObserver = observer;
    }

    @Override
    public void callString(String string) {
        try {
            mObserver.onNext(mConverter.convert(string));
            mObserver.onComplete();
        }catch (Exception e){
            mObserver.onError(e);
        }
    }
}
