package com.kenjin.submission4.Api;

import com.kenjin.submission4.Model.MovieResult;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiInterface {
    @GET("movie/popular")
    Call<MovieResult> getMoviePopular(@Query("api_key") String apiKey);

    @GET("search/movie")
    Call<MovieResult> searchMovie(@Query("api_key") String key,
                                  @Query("language") String bahasa,
                                  @Query("query") String search);

    @GET("movie/now_playing")
    Call<MovieResult> nowPlayingMovie(@Query("api_key") String key,
                                      @Query("language") String bahasa);

    @GET("movie/upcoming")
    Call<MovieResult> upCommingMovie(@Query("api_key") String key,
                                     @Query("language") String bahasa);
}
