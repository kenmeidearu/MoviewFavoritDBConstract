package com.kenjin.submission4.Ui.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kenjin.submission4.Adapter.MovieAdapter;
import com.kenjin.submission4.Api.ApiClient;
import com.kenjin.submission4.Api.ApiInterface;
import com.kenjin.submission4.BuildConfig;
import com.kenjin.submission4.Helper.Constant;
import com.kenjin.submission4.Model.Movie;
import com.kenjin.submission4.Model.MovieResult;
import com.kenjin.submission4.R;
import com.kenjin.submission4.Ui.Activity.DetailMovieActivity;
import com.kenjin.submission4.Ui.Activity.MovieActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NowPlayingFragment extends Fragment implements MovieAdapter.ListItemClickListener {
    RecyclerView recyclerView;
    ProgressBar progressBar;
    ArrayList<Movie> movies = new ArrayList<>();

    public NowPlayingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_now_playing, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        progressBar = view.findViewById(R.id.progress);
        movies = new ArrayList<>();

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                nowPlaying();

            }
        }, 2000);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                nowPlaying();

            }
        }, 2000);
    }

    @Override
    public void onListItemClick(int clickedItemIndex, Movie movie) {
        Intent intent = new Intent(getActivity(), DetailMovieActivity.class);
        intent.putExtra(DetailMovieActivity.sessionMovie, movie);
        startActivity(intent);
    }

    private void nowPlaying() {
        progressBar.setVisibility(View.VISIBLE);
        ApiInterface service = ApiClient.getClient().create(ApiInterface.class);
        Call<MovieResult> call = service.nowPlayingMovie(BuildConfig.API_KEY, Constant.LANG);
        call.enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(@NonNull Call<MovieResult> call, @NonNull Response<MovieResult> response) {
                try {
                    MovieResult apiResponse = response.body();
                    assert apiResponse != null;
                    movies = apiResponse.getMovie();
                    Log.e("tes",movies.get(0).getId()+", idnya");
                    loadRecyler();
                } catch (NullPointerException e) {
                    Log.e("err","err"+e.toString());
                    Toast.makeText(getActivity(), "Error Data"+e.toString(), Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(@NonNull Call<MovieResult> call, Throwable t) {
                Log.e("NowPlaying", "onFailure: " + t.toString());
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void loadRecyler() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        }
        MovieAdapter adapter = new MovieAdapter(movies, NowPlayingFragment.this);
        recyclerView.setAdapter(adapter);
    }
}
