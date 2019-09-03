package rx.com.wyn.rxjavademo.demo;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * Created by wangyn on 19/6/23.
 */

public class GsonConverterFactory implements Converter.Factory {
    private Gson mGson;

    public GsonConverterFactory(Gson gson){
        mGson = gson;
    }


    @Override
    public Converter<?> createConverter(Type type) {
        return new GsonConverter<>(mGson, type);
    }
}