package com.kenjin.submission4.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import static android.provider.BaseColumns._ID;
import static com.kenjin.submission4.database.DBContract.TABLE_Favorit;


public class FavoritHelper {

    private Context context;
    private DatabaseHelper dataBaseHelper;
    private SQLiteDatabase database;

    public FavoritHelper(Context context) {
        this.context = context;
    }
    public FavoritHelper open() throws SQLException {
        dataBaseHelper = new DatabaseHelper(context);
        database = dataBaseHelper.getWritableDatabase();
        return this;
    }
    public void close(){
        dataBaseHelper.close();
    }

    public Cursor queryProvider (){
        return database.query(TABLE_Favorit,null,null,null,null,null,_ID+" DESC");

    }
    public Cursor queryByIdProvider(String idMovie){
        return database.query(TABLE_Favorit,null,_ID + " = ?"
                ,new String[]{idMovie}
                ,null
                ,null
                ,null
                ,null);
    }
    public long insertProvider(ContentValues values){
        return database.insert(TABLE_Favorit,null,values);
    }
    public int updateProvider(String id, ContentValues values){
        return database.update(TABLE_Favorit,values,_ID +" = ?",new String[]{id} );
    }
    public int deleteProvider(String id){
        return database.delete(TABLE_Favorit,_ID + " = ?", new String[]{id});
    }


}
