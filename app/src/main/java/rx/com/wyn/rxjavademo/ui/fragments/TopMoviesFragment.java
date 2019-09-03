package rx.com.wyn.rxjavademo.ui.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import rx.com.wyn.rxjavademo.R;
import rx.com.wyn.rxjavademo.adapter.MoviesRecyclerViewAdapter;
import rx.com.wyn.rxjavademo.model.Subject;
import rx.com.wyn.rxjavademo.service.DoubanOperator;
import rx.com.wyn.rxjavademo.service.MovieOperator;

/**
 * Created by wangyn on 17/11/13.
 */

public class TopMoviesFragment extends LazyFragment {

    private String TAG = "TopMoviesFragment";
    private boolean isPrepared;
    private View view;
    private RecyclerView mRecyclerView;
    private MoviesRecyclerViewAdapter mTopRecyclerViewAdapter;
    private Context mContext;
    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        Log.v(TAG, "lazyLoad");
        if (null == mTopRecyclerViewAdapter) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            mRecyclerView.setLayoutManager(linearLayoutManager);
            mTopRecyclerViewAdapter = new MoviesRecyclerViewAdapter(this.getContext(), null);
            mRecyclerView.setAdapter(mTopRecyclerViewAdapter);
//            mTopRecyclerViewAdapter.setGetMoreMoviesListener(new GetMoreMoviesListener() {
//                @Override
//                public void getMoreMovies() {
//                    getMovies(mTopRecyclerViewAdapter.getItemCount(), 20);
//                }
//            });
//            mTopRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
//                @Override
//                public void onClick(View v, int position) {
//                    Intent intent = new Intent(getActivity(), MoviesDetailActivity.class);
//                    intent.putExtra(KEY_ID, v.getTransitionName());
//                    intent.putExtra("bean", mTopRecyclerViewAdapter.getItem(position));
//                    intent.putExtra("bitmap", drawableToBitmap(((ImageView) v.findViewById(R.id.img_movie)).getDrawable()));
//                    ActivityOptions activityOptions
//                            = ActivityOptions.makeSceneTransitionAnimation(getActivity(), v.findViewById(R.id.img_movie), "img");
//
//                    startActivity(intent, activityOptions.toBundle());
//                }
//            });
        }
    }

    @Override
    public void onFirstVisible() {
        getMoviesWithProgress(0, 15);
    }
    private void getMoviesWithProgress(int start, int count) {
        Log.v(TAG, "getMoviesWithProgress");
        Observer observer = new Observer<List<Subject>>() {

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<Subject> subjects) {
                if(mTopRecyclerViewAdapter!=null) {
                    mTopRecyclerViewAdapter.addList(subjects);
                }
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
        MovieOperator.getTopMovie250(observer,start, count);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_usbox_movies, container, false);
        mContext = this.getContext();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_usbox);
        isPrepared = true;
        lazyLoad();
        return view;
    }
    public static TopMoviesFragment newInstance() {
        TopMoviesFragment fragment = new TopMoviesFragment();
        return fragment;
    }

}
