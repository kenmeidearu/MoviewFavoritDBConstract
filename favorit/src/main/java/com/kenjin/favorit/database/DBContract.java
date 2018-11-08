package com.kenjin.favorit.database;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;


public class DBContract {
    public static String TABLE_Favorit = "movie";
    public static final class FavoritColumn implements BaseColumns {
        public static String TITLE = "title";
        public static String RATE = "rate";
        public static String DATE = "date";
        public static String POSTER = "poster";
    }
    public static final String AUTHORITY = "com.kenjin.submission4";
    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_Favorit)
            .build();
    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString( cursor.getColumnIndex(columnName) );
    }
    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt( cursor.getColumnIndex(columnName) );
    }
    public static long getColumnLong(Cursor cursor, String columnName) {
        return cursor.getLong( cursor.getColumnIndex(columnName) );
    }
    public static double getColumnDouble(Cursor cursor, String columnName) {
        return cursor.getDouble( cursor.getColumnIndex(columnName) );
    }
}
