package com.kenjin.submission4.Ui.Activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import com.kenjin.submission4.Adapter.FavoriteAdapter;
import com.kenjin.submission4.Model.Movie;
import com.kenjin.submission4.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.kenjin.submission4.database.DBContract.CONTENT_URI;


public class FavoritActivity extends AppCompatActivity  {
    @BindView(R.id.rvFavorite)
    RecyclerView recyclerView;
    private Cursor list;
    FavoriteAdapter  adapter;
    private static final String TAG = FavoritActivity.class.getSimpleName();

    ArrayList<Movie> movies=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        ButterKnife.bind(this);
        setTitle("List Favorit");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        loadRecyler();
        new LoadData().execute();

    }


    private void loadRecyler(){
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        adapter = new FavoriteAdapter(this);
        adapter.setListCursor(list);
        recyclerView.setAdapter(adapter);
    }

    private class LoadData  extends AsyncTask<Void, Void, Cursor> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return getContentResolver().query(CONTENT_URI,null,null,null,null);
        }

        @Override
        protected void onPostExecute(Cursor notes) {
            super.onPostExecute(notes);

            list = notes;
            adapter.setListCursor(list);
            adapter.notifyDataSetChanged();
            if(list!=null) {
                if (list.getCount() == 0) {
                    showSnackbarMessage("Tidak ada data saat ini");
                }
            }else{
                showSnackbarMessage("Tidak ada data saat ini");
            }
        }}


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void showSnackbarMessage(String message){
        Snackbar.make(recyclerView, message, Snackbar.LENGTH_SHORT).show();
    }
}
