package rx.com.wyn.rxjavademo.service;


import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.com.wyn.rxjavademo.BuildConfig;

/**
 * Created by wangyn on 17/9/7.
 */

public class RetrofitProvider {
    //https://api.douban.com/v2/movie/top250?start=0&count=15
    private static final String ENDPOINT = "https://api.douban.com/" ;//base_url

    public static Retrofit get(String endpoint){

            OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
            builder.readTimeout(10, TimeUnit.SECONDS);
            builder.connectTimeout(9, TimeUnit.SECONDS);

            if (BuildConfig.DEBUG) {//能统一打印请求与响应的json
                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                builder.addInterceptor(interceptor);
            }

            return new Retrofit.Builder().baseUrl(endpoint)
                    .client(builder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    //表示将服务端返回的json数据通过GsonConverterFactory完成解析转化
                    //此处添加RxJavaCallAdapterFactory，把请求结果直接映射为
                    //MovieService接口方法返回的具体类型MovieEntity
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
    }
}
