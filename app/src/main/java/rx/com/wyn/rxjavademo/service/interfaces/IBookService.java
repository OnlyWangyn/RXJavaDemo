package rx.com.wyn.rxjavademo.service.interfaces;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.com.wyn.rxjavademo.model.BookInfo;

/**
 * Created by nancy on 17-10-15.
 */

public interface IBookService {
    @GET("{id}")
    Observable<BookInfo> getBookInfo(@Path("id") String id);
}
