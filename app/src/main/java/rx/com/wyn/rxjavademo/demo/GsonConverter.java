package rx.com.wyn.rxjavademo.demo;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * Created by wangyn on 19/6/23.
 */

public class GsonConverter <T> implements Converter<T> {
    private final Gson mGson;
    private final Type mType;

    public GsonConverter(Gson gson, Type type){
        mGson = gson;
        mType = type;
    }

    @Override
    public T convert(String str) {
        return mGson.fromJson(str, mType);
    }
}
