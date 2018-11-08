package com.kenjin.submission4.Ui.Activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
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

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieActivity extends AppCompatActivity implements MovieAdapter.ListItemClickListener {
    @BindView(R.id.txtSearch)
    EditText txtsearch;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progress)
    ProgressBar progressBar;
    private static final String TAG = MovieActivity.class.getSimpleName();

    ArrayList<Movie> movies=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        ButterKnife.bind(this);
        setTitle("Search Movie");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        progressBar.setVisibility(View.VISIBLE);
        loadRecyler();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                GetMoview();
            }
        }, 2000);


    }

    @OnClick(R.id.btnSearch)
    void cari(){
        String cari=txtsearch.getEditableText().toString();
        progressBar.setVisibility(View.VISIBLE);
        SearchMovie(cari);
    }

    private void loadRecyler(){
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        }
        MovieAdapter adapter = new MovieAdapter(movies, MovieActivity.this);
        recyclerView.setAdapter(adapter);
    }

    private void GetMoview(){
        ApiInterface service = ApiClient.getClient().create(ApiInterface.class);
        Call<MovieResult> call = service.getMoviePopular(BuildConfig.API_KEY);
        call.enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {
                try {

                    MovieResult apiResponse = response.body();
                    movies = apiResponse.getMovie();
                    //MovieAdapter adapter = new MovieAdapter(movies, MovieActivity.this);
                    loadRecyler();
                }catch (NullPointerException e){
                    Toast.makeText(getApplicationContext(),"Error Data",Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<MovieResult> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void SearchMovie(String search){
        ApiInterface service = ApiClient.getClient().create(ApiInterface.class);
        Call<MovieResult> call = service.searchMovie(BuildConfig.API_KEY,Constant.LANG,search);
        call.enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {
                try {

                    MovieResult apiResponse = response.body();
                    movies = apiResponse.getMovie();
                    loadRecyler();
                }catch (NullPointerException e){
                    Toast.makeText(getApplicationContext(),"Error Data",Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<MovieResult> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onListItemClick(int clickedItemIndex, Movie movie) {
        Intent intent = new Intent(MovieActivity.this, DetailMovieActivity.class);
        intent.putExtra(DetailMovieActivity.sessionMovie, movie);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
