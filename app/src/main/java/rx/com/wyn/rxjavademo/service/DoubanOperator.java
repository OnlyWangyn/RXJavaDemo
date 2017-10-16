package rx.com.wyn.rxjavademo.service;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import rx.com.wyn.rxjavademo.model.BookInfo;
import rx.com.wyn.rxjavademo.model.MovieInfo;
import rx.com.wyn.rxjavademo.model.Subject;
import rx.com.wyn.rxjavademo.service.interfaces.IBookService;
import rx.com.wyn.rxjavademo.service.interfaces.IMovieService;


/**
 * Created by wangyn on 17/9/7.
 */

public class DoubanOperator {

    //https://api.douban.com/v2/movie/top250?start=0&count=15
    private static final String MOVIE_ENDPOINT = "https://api.douban.com/" ;//movie url
    //https://api.douban.com/v2/book/gettopbooks/start/0/num/10
    private static final String BOOK_ENDPOINT = "https://api.douban.com/v2/book/";
    /**
     * 用于获取豆瓣电影Top250的数据
     * @param start 起始位置
     * @param count 获取长度
     */
    public static void getTopMovie250(int start,int count){
        IMovieService movieService = RetrofitProvider.get(MOVIE_ENDPOINT).create(IMovieService.class);
        movieService.getMovie(start,count)
                .subscribeOn(Schedulers.io())
                .map(new Function<MovieInfo, List<Subject>>() {
                    @Override
                    public List<Subject> apply( MovieInfo movieInfo) throws Exception {
                        return movieInfo.getSubjects();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Observer< List<Subject>>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(List<Subject> value) {
                    Log.d("c2","result size:"+value.size());
                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onComplete() {

                }
            });
    }
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
    public static void getBookAndMovieInfo(final Context context){
        IMovieService movieService = RetrofitProvider.get(MOVIE_ENDPOINT).create(IMovieService.class);
        Observable movieObservable = movieService.getMovie(0,10).subscribeOn(Schedulers.io());
        IBookService bookService =  RetrofitProvider.get(BOOK_ENDPOINT).create(IBookService.class);
        Observable bookObservable = bookService.getBookInfo("6548683").subscribeOn(Schedulers.io());
        Observable.zip(movieObservable, bookObservable, new BiFunction<MovieInfo,BookInfo,String>() {

            @Override
            public String apply(MovieInfo movie, BookInfo book) throws Exception {
                Subject subject = movie.getSubjects().get(0);
                String str = "I like this movie %s and this book %s";
                return String.format(str,subject.getTitle(),book.getTitle());
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
            @Override
            public void accept(String str) throws Exception {
                Toast.makeText(context,str,Toast.LENGTH_LONG).show();
            }
        });
    }
}

