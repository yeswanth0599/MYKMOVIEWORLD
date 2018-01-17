package com.mykmovies.android.mykmovieworld.data;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by yeswa on 10-01-2018.
 */

public class MovieContentProvider extends ContentProvider {

    /**
     * UriMatcher implementation
     * Two Types of UriMatcher
     * 1. Operation on entire table
     * 2. Operation on Particualr row of a table
     */
    public static final int FAVORITE_MOVIES=100;
    public static final int FAVORITE_MOVIES_ID=110;
    private static UriMatcher sUriMatcher=buildUriMatcher();

    public static UriMatcher buildUriMatcher()
    {
        UriMatcher uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(MovieContract.AUTHORITY,MovieContract.PATH,FAVORITE_MOVIES);
        uriMatcher.addURI(MovieContract.AUTHORITY,MovieContract.PATH+"/#",FAVORITE_MOVIES_ID);
        return uriMatcher;
    }

    /**
     * Creating global variable for MovieDBHelper class
     * implementing on onCreate() method
     */
    MovieDBHelper movieDBHelper;
    @Override
    public boolean onCreate() {

        movieDBHelper=new MovieDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        // Get access to underlying database (read-only for query)
        final SQLiteDatabase sqLiteDatabase = movieDBHelper.getReadableDatabase();

        // Write URI match code and set a variable to return a Cursor
        int match = sUriMatcher.match(uri);
        Cursor retCursor;

        // Query for the tasks directory and write a default case
        switch (match) {
            // Query for the tasks directory
            case FAVORITE_MOVIES:
                retCursor =  sqLiteDatabase.query(MovieContract.MovieEntryInfo.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            // Default exception
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // Set a notification URI on the Cursor and return that Cursor
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);

        // Return the desired Cursor
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    /**
     * Insert method implementaiton
     * for insert allways use of UriMatcher 100, because insert operation can done on entire table
     */
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {

        final SQLiteDatabase sqLiteDatabase=movieDBHelper.getWritableDatabase();
        int match=sUriMatcher.match(uri);

        Uri returnInsertUri;

        switch (match) {
            case FAVORITE_MOVIES:
                long id=sqLiteDatabase.insert(MovieContract.MovieEntryInfo.TABLE_NAME,null,contentValues);
                if(id>0)
                {
                    returnInsertUri=ContentUris.withAppendedId(MovieContract.MovieEntryInfo.CONTENT_URI,id);
                }
                else
                {
                    throw new android.database.SQLException("Movie Details not inserted"+uri);
                }
                break;

            default:
                throw new UnsupportedOperationException("UnKnown Uri"+uri);
        }

        getContext().getContentResolver().notifyChange(uri,null);

        return returnInsertUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        // COMPLETED (1) Get access to the database and write URI matching code to recognize a single item
        final SQLiteDatabase sqLiteDatabase = movieDBHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);
        // Keep track of the number of deleted tasks
        int tasksDeleted; // starts as 0

        // COMPLETED (2) Write the code to delete a single row of data
        // [Hint] Use selections to delete an item by its row ID
        switch (match) {
            // Handle the single item case, recognized by the ID included in the URI path
            case FAVORITE_MOVIES_ID:
                // Get the task ID from the URI path
                String movie_id = uri.getPathSegments().get(1);
                // Use selections/selectionArgs to filter for this ID
                tasksDeleted = sqLiteDatabase.delete(MovieContract.MovieEntryInfo.TABLE_NAME, "movie_id=?", new String[]{movie_id});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // COMPLETED (3) Notify the resolver of a change and return the number of items deleted
        if (tasksDeleted != 0) {
            // A task was deleted, set notification
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // Return the number of tasks deleted
        return tasksDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
