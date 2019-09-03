package rx.com.wyn.rxjavademo.service;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import rx.com.wyn.rxjavademo.model.MovieInfo;
import rx.com.wyn.rxjavademo.model.Subject;
import rx.com.wyn.rxjavademo.model.Subjects;
import rx.com.wyn.rxjavademo.model.USbox;
import rx.com.wyn.rxjavademo.service.interfaces.IMovieService;

/**
 * Created by wangyn on 17/11/15.
 */

public class MovieOperator {
    private static final String MOVIE_ENDPOINT = "https://api.douban.com/v2/movie/" ;
    private static MovieOperator movieOperator = null;
    private IMovieService movieService;
    private MovieOperator(){
        movieService = RetrofitProvider.get(MOVIE_ENDPOINT).create(IMovieService.class);
    }
    public static MovieOperator getInstance(){
        if(movieOperator == null){
            movieOperator = new MovieOperator();
        }
        return movieOperator;
    }

    /**
     * 用于获取豆瓣电影Top250的数据
     * @param start 起始位置
     * @param count 获取长度
     */
    public static void getTopMovie250(Observer<List<Subject>> observer, int start, int count){
        IMovieService movieService = RetrofitProvider.get(MOVIE_ENDPOINT).create(IMovieService.class);
        movieService.getMovie(start,count)
                .subscribeOn(Schedulers.io())
                .map(new Function<MovieInfo, List<Subject>>() {
                    @Override
                    public List<Subject> apply(MovieInfo movieInfo) throws Exception {
                        return movieInfo.getSubjects();
                    }
                }).observeOn(AndroidSchedulers.mainThread()).subscribe(observer);
    }

    /**
     * 用于获取北美票房
     */
    public static void getUSBox(Observer<List<Subject>> observer){
        IMovieService movieService =RetrofitProvider.get(MOVIE_ENDPOINT).create(IMovieService.class);
        movieService.getUSBox().map(new Function<USbox, List<Subject>>() {
            @Override
            public List<Subject> apply(USbox uSbox) throws Exception {
                List<Subject> list = new ArrayList<Subject>();
                for(Subjects ss:uSbox.getSubjects()){
                    list.add(ss.getSubject());
                }
                return list;
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
