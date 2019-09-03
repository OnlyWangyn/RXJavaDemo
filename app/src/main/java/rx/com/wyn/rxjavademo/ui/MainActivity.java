package rx.com.wyn.rxjavademo.ui;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import rx.com.wyn.rxjavademo.R;
import rx.com.wyn.rxjavademo.adapter.MainFragmentAdapter;
import rx.com.wyn.rxjavademo.demo.GsonConverterFactory;
import rx.com.wyn.rxjavademo.demo.MockService;
import rx.com.wyn.rxjavademo.demo.RxJavaCallbackHelper;
import rx.com.wyn.rxjavademo.ui.fragments.ChartBooksFragment;
import rx.com.wyn.rxjavademo.ui.fragments.TopBooksFragment;
import rx.com.wyn.rxjavademo.ui.fragments.TopMoviesFragment;
import rx.com.wyn.rxjavademo.ui.fragments.USboxMoviesFragment;

public class MainActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener,
        USboxMoviesFragment.OnFragmentInteractionListener, TopMoviesFragment.OnFragmentInteractionListener,
        TopBooksFragment.OnFragmentInteractionListener,ChartBooksFragment.OnFragmentInteractionListener {
    private String TAG = "AppMainActivity";
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private List<Fragment> fragmentList;
    private List<String> fragmentTitles;
    private MainFragmentAdapter adapter;
    int currentType = 1;
    private static int MOVIE = 1;
    private static int BOOK = 2;
    private Handler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //
        MockService mockService = new MockService();
        RxJavaCallbackHelper helper = new RxJavaCallbackHelper(new GsonConverterFactory(new Gson()));
//        ResultHolder<MockData, StringCallback> holder = helper.create(new MockData.class, StringCallback.class);
//        mockService.getName("", holder.callback);
//        holder.observable.subscribe(mockData -> System.out.println("mock data code:" + mockData.code),
//                (e)->{}, ()->System.out.println("completed!"));
//

        DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mViewPager = (ViewPager) findViewById(R.id.viewpager_main);
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        fragmentList = new ArrayList<>();
        fragmentTitles = new ArrayList<>();
        fragmentList.add(USboxMoviesFragment.newInstance());
        fragmentList.add(TopMoviesFragment.newInstance());
        fragmentTitles.add("Recent");
        fragmentTitles.add("Top");
        adapter = new MainFragmentAdapter(getSupportFragmentManager(), fragmentList, fragmentTitles);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
//        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_top);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
//        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        mRecyclerView.setLayoutManager(linearLayoutManager);
//        mTopRecyclerViewAdapter = new MoviesRecyclerViewAdapter(mContext, null);


//        mRecyclerView.setAdapter(mTopRecyclerViewAdapter);
//        mTopRecyclerViewAdapter.setGetMoreMoviesListener(new GetMoreMoviesListener() {
//            @Override
//            public void getMoreMovies() {
//                getMovies(mTopRecyclerViewAdapter.getItemCount(), 20);
//            }
//        });
//        getMovies(0, 20);
//        RxTextView.beforeTextChangeEvents(tv).subscribe(new Consumer<TextViewBeforeTextChangeEvent>() {
//            @Override
//            public void accept(TextViewBeforeTextChangeEvent textViewBeforeTextChangeEvent) throws Exception {
//
//            }
//        });
        Observable.concat(observeMemory(), observeDisk(), network)
              .firstElement()
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept( String s) throws Exception {
                        Log.d("test","最终获取的数据来源 =  "+ s);
                    }
                });
        Observable observable = Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(ObservableEmitter observableEmitter) throws Exception {
                observableEmitter.onNext(1);
                observableEmitter.onComplete();

            }
        });
        Observer observer = new Observer<Integer>(){

            @Override
            public void onSubscribe(Disposable disposable) {

            }

            @Override
            public void onNext(Integer integer) {

            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onComplete() {

            }
        };
        observable.observeOn(Schedulers.io()).subscribe(observer);
    }
//    private void getMovies(int start, int count) {
//        Log.v("MainActivity", "getMovies");
//        Observer observer = new Observer<List<Subject>>() {
//
//            @Override
//            public void onSubscribe(Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(List<Subject> subjects) {
//                mTopRecyclerViewAdapter.addList(subjects);
//            }
//
//            @Override
//            public void onError(Throwable t) {
//                Toast.makeText(mContext, "error:" + t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onComplete() {
//                Toast.makeText(mContext, "加载成功", Toast.LENGTH_SHORT).show();
//            }
//        };
//       DoubanOperator.getTopMovie250(observer,start,count);
//
//    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_movie) {
            if (currentType != MOVIE) {
                Log.v(TAG, "R.id.nav_movie");
                currentType = MOVIE;
                fragmentList.clear();
                fragmentTitles.clear();
                fragmentList.add(USboxMoviesFragment.newInstance());
                fragmentList.add(TopMoviesFragment.newInstance());
                fragmentTitles.add("Recent");
                fragmentTitles.add("Top");
                adapter.setFragmentsAndTitle(fragmentList, fragmentTitles);
                adapter.notifyDataSetChanged();
                mTabLayout.setupWithViewPager(mViewPager);
            }
        } else if (id == R.id.nav_book) {
            Log.v(TAG, "R.id.nav_book");
            if (currentType != BOOK) {
                currentType = BOOK;
                fragmentList.clear();
                fragmentTitles.clear();
              //  fragmentList.add(ChartBooksFragment.newInstance("", ""));
               // fragmentList.add(TopBooksFragment.newInstance("", ""));
                fragmentTitles.add("Chart");
                fragmentTitles.add("Top");
                adapter.setFragmentsAndTitle(fragmentList, fragmentTitles);
                adapter.notifyDataSetChanged();
                mTabLayout.setupWithViewPager(mViewPager);
            }
        } else if (id == R.id.about) {
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
    private String memoryCache = null;
    private Observable observeMemory(){
        Observable<String> memory = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                // 先判断内存缓存有无数据
                if (memoryCache != null) {
                    // 若有该数据，则发送
                    emitter.onNext(memoryCache);
                    emitter.onComplete();

                } else {
                    // 若无该数据，则直接发送结束事件
                    emitter.onComplete();
              }
//                Log.d("test","this is 1");
//                emitter.onNext("m1");
//                emitter.onComplete();
            }
        });
        return memory;
    }
    private String diskCache = "从磁盘缓存中获取数据";
    private Observable observeDisk(){
        Observable<String> disk = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(final ObservableEmitter<String> emitter) throws Exception {
                if (diskCache != null) {
                    emitter.onNext(diskCache);
                    emitter.onComplete();
                } else {
                    emitter.onComplete();
                }
//                Log.d("test","this is m2");
//                emitter.onNext("m2");
//                emitter.onComplete();
            }
        });
        return disk;
    }
    Observable<String> network = Observable.just("从网络中获取数据");
}
