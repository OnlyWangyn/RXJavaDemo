package rx.com.wyn.rxjavademo.demo;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by wangyn on 19/6/23.
 */

public class RxJavaCallbackHelper {
    private final Converter.Factory mConverterFactory;
    private Map<TypeToken<?>, TypeAdapter<?>> mCached;


    public RxJavaCallbackHelper(Converter.Factory factory){
        mConverterFactory = factory;
        mCached = new HashMap<>();
        mCached.put(TypeToken.get(StringCallback.class), new StringCallbackAdapter<>());
    }

    public <T,R> ResultHolder<T,R> create(Type resultType, Type callbackType){
        ResultHolder<T, R> resultHolder = new ResultHolder<>();
        PublishSubject<T> subject = PublishSubject.create();
        resultHolder.observable = subject;
        TypeAdapter<T> typeAdapter = getAdapter(callbackType);
        //resultHolder.callback = typeAdapter.getCallback(getConverter(resultType), subject);
        return resultHolder;
    }

    @SuppressWarnings("unchecked")
    public <T> TypeAdapter<T> getAdapter(Type type){
        return (TypeAdapter<T>) mCached.get(TypeToken.get(type));
    }

    @SuppressWarnings("unchecked")
    public <T> Converter<T> getConverter(Type type){
        return (Converter<T>) mConverterFactory.createConverter(type);
    }
}

 interface TypeAdapter<T>{
    <R> R getCallback(Converter<T> converter, Observer<T> observer);
}

 interface Converter<T> {
    T convert(String str);

    interface Factory{
        Converter<?> createConverter(Type type);
    }
}
