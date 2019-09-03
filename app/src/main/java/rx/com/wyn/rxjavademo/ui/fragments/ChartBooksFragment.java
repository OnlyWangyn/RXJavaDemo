package rx.com.wyn.rxjavademo.ui.fragments;

import android.net.Uri;

/**
 * Created by wangyn on 17/11/13.
 */

public class ChartBooksFragment extends LazyFragment {
    @Override
    protected void lazyLoad() {

    }

    @Override
    public void onFirstVisible() {

    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
