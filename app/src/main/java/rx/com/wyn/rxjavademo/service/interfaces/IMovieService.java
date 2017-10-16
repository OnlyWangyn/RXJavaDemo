package rx.com.wyn.rxjavademo.service.interfaces;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.com.wyn.rxjavademo.model.MovieInfo;

/**
 * Created by wangyn on 17/10/10.
 */

public interface IMovieService {
    //Observable事件流被观察者，它决定什么时候触发事件以及触发怎样的事件
    @GET("v2/movie/top250?")
    Observable<MovieInfo> getMovie(@Query("start") int start,
                                   @Query("count") int count);
}
