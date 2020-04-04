package com.example.wikipedia.Controllers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "favoriteDB";
    static final String TABLE_FAVORITES = "favorites";

    private static final String KEY_ID = "_id";
    static final String KEY_TITLE = "title";
    static final String KEY_PAGEID = "pageid";

    DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "
                + TABLE_FAVORITES + "("
                + KEY_ID + " integer primary key,"
                + KEY_TITLE + " text,"
                + KEY_PAGEID + " text" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_FAVORITES);
        onCreate(db);
    }
}
