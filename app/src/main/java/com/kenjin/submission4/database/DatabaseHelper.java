package com.kenjin.submission4.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.kenjin.submission4.database.DBContract.TABLE_Favorit;


public class DatabaseHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "db_favorite";

    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_MOVIE = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            TABLE_Favorit,
            DBContract.FavoritColumn._ID,
            DBContract.FavoritColumn.TITLE,
            DBContract.FavoritColumn.RATE,
            DBContract.FavoritColumn.DATE,
            DBContract.FavoritColumn.POSTER

    );

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_MOVIE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_Favorit);
        onCreate(db);
    }


}
