package com.kenjin.submission4.Adapter;

import android.app.Activity;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kenjin.submission4.Helper.Constant;
import com.kenjin.submission4.Model.Movie;
import com.kenjin.submission4.R;
import com.squareup.picasso.Picasso;


public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.MyHolder> {
    private Cursor listCursor;
    private Activity activity;

    public void setListCursor(Cursor listcursor) {
        this.listCursor = listcursor;
    }

    public FavoriteAdapter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public FavoriteAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_movie, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(FavoriteAdapter.MyHolder holder, int position) {
        final Movie item = getItem(position);
        holder.title.setText(item.getOriginalTitle());
        Picasso.with(activity).load(Constant.IMAGE + item.getMoviePoster()).placeholder(R.mipmap.ic_launcher).fit().centerCrop().into(holder.image);
    }

    private Movie getItem(int position){
        if (!listCursor.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid");
        }
        return new Movie(listCursor);
    }
    @Override
    public int getItemCount() {
        if (listCursor == null) return 0;
        return listCursor.getCount();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title,txtRating;
        public MyHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.img);
            title = (TextView) itemView.findViewById(R.id.txtJudul);
            txtRating = itemView.findViewById(R.id.txtRating);
        }
    }
}
