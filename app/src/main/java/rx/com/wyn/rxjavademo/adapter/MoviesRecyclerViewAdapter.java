package rx.com.wyn.rxjavademo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import rx.com.wyn.rxjavademo.R;
import rx.com.wyn.rxjavademo.model.Subject;

/**
 * Created by nancy on 17-10-17.
 */

public class MoviesRecyclerViewAdapter extends RecyclerView.Adapter<MoviesRecyclerViewAdapter.ViewHolder> {
    private String TAG = "MoviesViewAdapter";
    private Context mContext;
    private GetMoreMoviesListener getMoreMoviesListener;
    List<Subject> subjects;
    AdapterView.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public MoviesRecyclerViewAdapter(Context mContext, List<Subject> subjects) {
        this.mContext = mContext;
        if (null != subjects)
            this.subjects = subjects;
        else
            this.subjects = new ArrayList<>();

    }

    public void setGetMoreMoviesListener(GetMoreMoviesListener getMoreMoviesListener) {
        this.getMoreMoviesListener = getMoreMoviesListener;
    }

    public void setList(List<Subject> subjects) {
        this.subjects.clear();
        this.subjects.addAll(subjects);
        notifyDataSetChanged();
    }

    public void addList(List<Subject> subjects) {
        this.subjects.addAll(subjects);
        notifyDataSetChanged();
    }

    @Override
    public MoviesRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.mView = view;
        viewHolder.mTitle = (TextView) view.findViewById(R.id.tv_movie_title);
        viewHolder.mDate = (TextView) view.findViewById(R.id.tv_movie_pubdates);
        viewHolder.mRate = (TextView) view.findViewById(R.id.tv_movie_rating);
        viewHolder.mCasts = (TextView) view.findViewById(R.id.tv_movie_casts);
        viewHolder.mDirectors = (TextView) view.findViewById(R.id.tv_movie_directors);
        viewHolder.mImage = (ImageView) view.findViewById(R.id.img_movie);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MoviesRecyclerViewAdapter.ViewHolder holder, final int position) {
        Subject tmp = subjects.get(position);
        holder.mDate.setText(tmp.getYear());
        holder.mTitle.setText(tmp.getTitle());
        holder.mCasts.setText(tmp.getCasts().size() ==0?"未知主演":tmp.getCasts().get(0).getName());
        holder.mDirectors.setText(tmp.getDirectors().size() ==0?"未知导演":tmp.getDirectors().get(0).getName());
        holder.mRate.setText(tmp.getRating().getAverage() + "");
        Glide.with(mContext).load(tmp.getImages().getMedium()).into(holder.mImage);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        if (position == subjects.size() - 1) {
            Log.v(TAG, "get more movies");
            if (null != getMoreMoviesListener) {
                getMoreMoviesListener.getMoreMovies();
            }
        }

    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View mView;
        public TextView mTitle;
        public TextView mDate;
        public TextView mRate;
        public TextView mDirectors;
        public TextView mCasts;
        public ImageView mImage;

        public ViewHolder(View view) {
            super(view);
        }
    }
}
