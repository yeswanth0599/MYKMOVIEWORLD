package com.mykmovies.android.mykmovieworld.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by yeswa on 10-01-2018.
 */

public class MovieDBHelper extends SQLiteOpenHelper {

    // The name of the database
    private static final String DATABASE_NAME = "movieDBOrgg.db";

    // If you change the database schema, you must increment the database version
    private static final int VERSION = 1;

    public MovieDBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String CREATE_TABLE = "CREATE TABLE "  + MovieContract.MovieEntryInfo.TABLE_NAME + " (" +
                MovieContract.MovieEntryInfo.COLUMN_MOVIE_ID + " INTEGER PRIMARY KEY, " +
                MovieContract.MovieEntryInfo.COLUMN_MOVIE_TITLE + " TEXT NOT NULL, " +
                MovieContract.MovieEntryInfo.COLUMN_MOVIE_POSTER_URL + " TEXT NOT NULL, " +
                MovieContract.MovieEntryInfo.COLUMN_MOVIE_SYNOPSIS + " TEXT NOT NULL, " +
                MovieContract.MovieEntryInfo.COLUMN_MOVIE_RATING + " REAL NOT NULL, " +
                MovieContract.MovieEntryInfo.COLUMN_MOVIE_RELEASE_DATE + " TEXT NOT NULL, " +
                MovieContract.MovieEntryInfo.COLUMN_MOVIE_ORIGINAL_TITLE + " TEXT NOT NULL, " +
                MovieContract.MovieEntryInfo.COLUMN_MOVIE_TRAILER + " TEXT NOT NULL); ";

        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieContract.MovieEntryInfo.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
