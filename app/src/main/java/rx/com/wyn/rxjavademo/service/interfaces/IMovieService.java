package rx.com.wyn.rxjavademo.service.interfaces;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.com.wyn.rxjavademo.model.MovieInfo;
import rx.com.wyn.rxjavademo.model.USbox;

/**
 * Created by wangyn on 17/10/10.
 */

public interface IMovieService {
    @GET("v2/movie/top250?")
    Observable<MovieInfo> getMovie(@Query("start") int start,
                                   @Query("count") int count);
    @GET("v2/movie/us_box")
    Observable<USbox> getUSBox();

}
