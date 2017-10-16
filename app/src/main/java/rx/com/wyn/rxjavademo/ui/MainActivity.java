package rx.com.wyn.rxjavademo.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.reactivestreams.Subscription;

import java.util.List;
import io.reactivex.Observer;

import io.reactivex.disposables.Disposable;
import rx.com.wyn.rxjavademo.R;
import rx.com.wyn.rxjavademo.adapter.GetMoreMoviesListener;
import rx.com.wyn.rxjavademo.adapter.MoviesRecyclerViewAdapter;
import rx.com.wyn.rxjavademo.model.Subject;
import rx.com.wyn.rxjavademo.service.DoubanOperator;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private MoviesRecyclerViewAdapter mTopRecyclerViewAdapter;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        TextView tv = (TextView)findViewById(R.id.text);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DoubanOperator.getBookAndMovieInfo(MainActivity.this);
             // DoubanOperator.getTopMovie250(0,10);
              //  OtherOperator.operatorTwo();
            }
        });
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_top);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mTopRecyclerViewAdapter = new MoviesRecyclerViewAdapter(mContext, null);
        mRecyclerView.setAdapter(mTopRecyclerViewAdapter);
        mTopRecyclerViewAdapter.setGetMoreMoviesListener(new GetMoreMoviesListener() {
            @Override
            public void getMoreMovies() {
                getMovies(mTopRecyclerViewAdapter.getItemCount(), 20);
            }
        });
        getMovies(0, 20);
    }
    private void getMovies(int start, int count) {
        Log.v("MainActivity", "getMovies");
        Observer observer = new Observer<List<Subject>>() {

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<Subject> subjects) {
                mTopRecyclerViewAdapter.addList(subjects);
            }

            @Override
            public void onError(Throwable t) {
                Toast.makeText(mContext, "error:" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {
                Toast.makeText(mContext, "加载成功", Toast.LENGTH_SHORT).show();
            }
        };
       DoubanOperator.getTopMovie250(observer,start,count);
    }
}
