package com.kenjin.favorit.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kenjin.favorit.R;
import com.squareup.picasso.Picasso;

import static com.kenjin.favorit.database.DBContract.FavoritColumn.POSTER;
import static com.kenjin.favorit.database.DBContract.FavoritColumn.TITLE;
import static com.kenjin.favorit.database.DBContract.getColumnString;


public class FavoriteAdapter extends CursorAdapter {
    public FavoriteAdapter(Context context, Cursor c, boolean autoquery) {
        super(context, c, autoquery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_favorit, viewGroup, false);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        if (cursor != null) {

            ImageView image = view.findViewById(R.id.image);
            TextView title = view.findViewById(R.id.title);

            title.setText(getColumnString(cursor, TITLE));
            String url = "http://image.tmdb.org/t/p/w185" + getColumnString(cursor, POSTER);
            Picasso.with(context).load(url)
                                 .placeholder(R.mipmap.ic_launcher)
                                 .error(R.mipmap.ic_launcher)
                                 .into(image);


        }

    }
    @Override
    public Cursor getCursor() {
        return super.getCursor();
    }

}
