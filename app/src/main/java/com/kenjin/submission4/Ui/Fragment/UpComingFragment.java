package com.kenjin.submission4.Ui.Fragment;

import android.content.Intent;
import android.content.res.Configuration;
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

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpComingFragment extends Fragment implements MovieAdapter.ListItemClickListener {
    RecyclerView recyclerView;
    ProgressBar progressBar;
    ArrayList<Movie> movies = new ArrayList<>();

    public UpComingFragment() {
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
                comingPlaying();

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
                comingPlaying();

            }
        }, 2000);
    }

    @Override
    public void onListItemClick(int clickedItemIndex, Movie movie) {
        Intent intent = new Intent(getActivity(), DetailMovieActivity.class);
        intent.putExtra(DetailMovieActivity.sessionMovie, movie);
        startActivity(intent);
    }

    private void comingPlaying() {
        progressBar.setVisibility(View.VISIBLE);
        ApiInterface service = ApiClient.getClient().create(ApiInterface.class);
        Call<MovieResult> call = service.upCommingMovie(BuildConfig.API_KEY, Constant.LANG);
        call.enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(@NonNull Call<MovieResult> call, @NonNull Response<MovieResult> response) {
                try {

                    MovieResult apiResponse = response.body();
                    assert apiResponse != null;
                    movies = apiResponse.getMovie();
                    loadRecyler();
                } catch (NullPointerException e) {
                    Toast.makeText(getActivity(), "Error Data", Toast.LENGTH_SHORT).show();
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
        MovieAdapter adapter = new MovieAdapter(movies, UpComingFragment.this);
        recyclerView.setAdapter(adapter);
    }
}
