package com.kenjin.submission4.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kenjin.submission4.Helper.Constant;
import com.kenjin.submission4.Model.Movie;
import com.kenjin.submission4.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private static final String TAG = MovieAdapter.class.getSimpleName();
    private ArrayList<Movie> movies;
    private Context context;
    private ListItemClickListener mOnClickListener;

    public MovieAdapter(ArrayList<Movie> movies, ListItemClickListener listener) {
        this.movies = movies;
        mOnClickListener = listener;
    }

    public interface ListItemClickListener{
        void onListItemClick(int clickedItemIndex, Movie movie);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.list_movie, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Movie movie = movies.get(position);
        Log.i(TAG, "onBindViewHolder: Title " + movie.getOriginalTitle());
        Log.i(TAG, "onBindViewHolder: Poster " + Constant.IMAGE + movie.getMoviePoster());
        holder.txtJudul.setText(movie.getOriginalTitle());
        holder.txtRating.setText(String.format("Rating :%s", movie.getUserRating()));
        Picasso.with(context).load(Constant.IMAGE + movie.getMoviePoster()).placeholder(R.mipmap.ic_launcher).fit().centerCrop().into(holder.img);

        final Movie movie1 = movies.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnClickListener.onListItemClick(position, movie1);
            }
        });

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView txtJudul,txtRating;

        public ViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);

            txtJudul = itemView.findViewById(R.id.txtJudul);
            txtRating = itemView.findViewById(R.id.txtRating);
        }


    }
}
