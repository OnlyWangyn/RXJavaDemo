package rx.com.wyn.rxjavademo.ui.fragments;

import android.support.v4.app.Fragment;
import android.util.Log;

/**
 * Created by wangyn on 17/11/10.
 */

public abstract class LazyFragment extends Fragment {
    private String TAG = "LazyFragment";
    protected boolean isVisible = false;
    protected boolean isFirtsVisible = true;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
      super.setUserVisibleHint(isVisibleToUser);
        if(getUserVisibleHint()){
            isVisible = true;
            onVisible();
            if(isFirtsVisible){
                onFirstVisible();
                isFirtsVisible = false;
            }
        }else{
            isVisible = false;
            onInvisible();
        }
    }
    protected abstract void lazyLoad();
    public abstract void onFirstVisible();

    protected void onInvisible() {
        Log.v(TAG, "onInvisible");
    }

    private void onVisible() {
        Log.v(TAG, "onVisible");
        lazyLoad();
    }
}
