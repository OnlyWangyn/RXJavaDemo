package rx.com.wyn.rxjavademo.ui.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

public class USboxMoviesFragment extends LazyFragment {
    private static final String TAG = "USboxMoviesFragment";

    private MoviesRecyclerViewAdapter mUSboxRecyclerViewAdapter;

    private OnFragmentInteractionListener mListener;
    private View view;
    private RecyclerView mRecyclerView;
    private boolean isPrepared;
    private Context mContext;

    public USboxMoviesFragment() {

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public static USboxMoviesFragment newInstance() {
        USboxMoviesFragment fragment = new USboxMoviesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_usbox_movies, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_usbox);
        mContext = this.getContext();
        isPrepared = true;
        lazyLoad();
        Log.d(TAG,"onCreateView");
        return view;
    }

    private static final String KEY_ID = "ViewTransitionValues:id";

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        Log.d(TAG,"lazyLoad");
        if (mUSboxRecyclerViewAdapter == null) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            mRecyclerView.setLayoutManager(linearLayoutManager);
            mUSboxRecyclerViewAdapter = new MoviesRecyclerViewAdapter(this.getContext(), null);
            mRecyclerView.setAdapter(mUSboxRecyclerViewAdapter);

//            mUSboxRecyclerViewAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onClick(View v, int position) {
//                    Intent intent = new Intent(getActivity(), MoviesDetailActivity.class);
//                    intent.putExtra(KEY_ID, v.getTransitionName());
//                    intent.putExtra("bean", mUSboxRecyclerViewAdapter.getItem(position));
//                    intent.putExtra("type", "us");
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
        getUSBox();
        Log.d(TAG,"onFirstVisible");
    }

    private void getUSBox() {
        Observer observer = new Observer<List<Subject>>() {

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<Subject> subjects) {
                if(mUSboxRecyclerViewAdapter!=null) {
                    mUSboxRecyclerViewAdapter.addList(subjects);
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
        MovieOperator.getUSBox(observer);
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable == null)
            return null;
        Bitmap bitmap = Bitmap
                .createBitmap(
                        drawable.getIntrinsicWidth(),
                        drawable.getIntrinsicHeight(),
                        drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                                : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG,"onStop");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG,"onPause");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG,"onDetach");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG,"onDestroyView");
    }
}