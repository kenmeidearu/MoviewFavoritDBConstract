package com.kenjin.submission4.Ui.Activity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kenjin.submission4.Helper.Constant;
import com.kenjin.submission4.Model.Movie;
import com.kenjin.submission4.R;
import com.kenjin.submission4.database.FavoritHelper;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.provider.BaseColumns._ID;
import static com.kenjin.submission4.database.DBContract.CONTENT_URI;
import static com.kenjin.submission4.database.DBContract.FavoritColumn.DATE;
import static com.kenjin.submission4.database.DBContract.FavoritColumn.POSTER;
import static com.kenjin.submission4.database.DBContract.FavoritColumn.RATE;
import static com.kenjin.submission4.database.DBContract.FavoritColumn.TITLE;

public class DetailMovieActivity extends AppCompatActivity {
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.title)
    TextView judul;
    @BindView(R.id.release)
    TextView release;
    @BindView(R.id.rating)
    TextView rating;
    @BindView(R.id.detail)
    TextView detail;
    @BindView(R.id.btnFavorit)
    Button favorit;

    public static String sessionMovie = "sessionMovie";
    private Movie movie;
    FavoritHelper helper;
    boolean Fav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        if (getIntent().getParcelableExtra(sessionMovie) != null) {
            movie = getIntent().getParcelableExtra(sessionMovie);
            Picasso.with(this).load(Constant.IMAGE + movie.getMoviePoster()).placeholder(R.mipmap.ic_launcher).fit().centerCrop().into(img);
            judul.setText(movie.getOriginalTitle());
            setTitle(movie.getOriginalTitle());
            release.setText(String.format("Update at:%s", movie.getReleaseDate()));
            rating.setText(movie.getUserRating());
            detail.setText(movie.getPlotSynopsis());
        }
        Fav = CheckFav();
        if (Fav) {
            favorit.setBackgroundResource(android.R.drawable.btn_star_big_on);
        } else {
            favorit.setBackgroundResource(android.R.drawable.btn_star_big_off);
        }
    }

    @OnClick(R.id.btnFavorit)
    void gantivaf() {
        if (Fav) {
            removeFavorite();
        } else {
            saveFavorite();
        }
        Fav = CheckFav();
    }

    private void saveFavorite() {
        ContentValues values = new ContentValues();
        values.put(_ID, movie.getId());
        values.put(TITLE, movie.getOriginalTitle());
        values.put(RATE, movie.getUserRating());
        values.put(DATE, movie.getReleaseDate());
        values.put(POSTER, movie.getMoviePoster());
        getContentResolver().insert(CONTENT_URI, values);
        setResult(1);
        favorit.setBackgroundResource(android.R.drawable.btn_star_big_on);
        Toast.makeText(this, "Save To Favorit", Toast.LENGTH_SHORT).show();
    }

    private void removeFavorite() {
        getContentResolver().delete(
                Uri.parse(CONTENT_URI + "/" + movie.getId()),
                null,
                null
        );
        setResult(2);
        favorit.setBackgroundResource(android.R.drawable.btn_star_big_off);
        Toast.makeText(this, "Remove From Favorit", Toast.LENGTH_SHORT).show();

    }

    boolean CheckFav() {
        boolean isfav = false;
        helper = new FavoritHelper(this);
        helper.open();
        if (movie != null) {
            Cursor cursor = getContentResolver().query(
                    Uri.parse(CONTENT_URI + "/" + movie.getId()),
                    null,
                    null,
                    null,
                    null
            );
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    isfav = true;
                    cursor.close();
                }
            }
        }else{
            Log.e("isi", "tidak ada vak" + Fav);
        }
        return isfav;
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (helper != null) {
            helper.close();
        }
    }
}
