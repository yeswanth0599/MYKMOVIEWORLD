package com.mykmovies.android.mykmovieworld.data;

import android.net.Uri;
import android.provider.BaseColumns;

import com.mykmovies.android.mykmovieworld.BuildConfig;

/**
 * Created by yeswa on 04-01-2018.
 */

public class MovieContract {

    public static final String MOVIE_BASE_URL="https://api.themoviedb.org/3/movie/";
    public static final String POPULAR_MOVIE_BASE_URL=MOVIE_BASE_URL.concat("popular?api_key=");
    public static final String TOP_MOVIE_BASE_URL=MOVIE_BASE_URL.concat("top_rated?api_key=");
    public static final String AUTHORITY="com.mykmovies.android.mykmovieworld";
    public static final Uri BASE_CONTENT_AUTHORITY=Uri.parse("content://"+AUTHORITY);
    public static final String PATH="fav_movies";

    public static final class MovieEntryInfo implements BaseColumns
    {
        public static final Uri CONTENT_URI=BASE_CONTENT_AUTHORITY.buildUpon().appendPath(PATH).build();
        /******************************************************************************/
        /*********************Database Constants************************************/
        /******************************************************************************/
        public static final String TABLE_NAME = "fav_movies";
        // "_ID" column in addition to the two below
        public static final String COLUMN_MOVIE_ID ="movie_id";
        public static final String COLUMN_MOVIE_TITLE = "movie_name";
        public static final String COLUMN_MOVIE_POSTER_URL = "movie_title";
        public static final String COLUMN_MOVIE_SYNOPSIS = "movie_synopsis";
        public static final String COLUMN_MOVIE_RATING = "movie_rating";
        public static final String COLUMN_MOVIE_RELEASE_DATE = "movie_release_date";
        public static final String COLUMN_MOVIE_ORIGINAL_TITLE = "movie_original_title";
        public static final String COLUMN_MOVIE_TRAILER = "movie_trailer";

        /**********************************************************************************/
        public static final String MOVIE_DB_KEY=BuildConfig.MovieDBApiKeyInfo;
        public static final String YOUTUBE_API_KEY=BuildConfig.YoutubeApiKeyInfo;
        public static final String POPULAR_MOVIE_URL=POPULAR_MOVIE_BASE_URL.concat(MOVIE_DB_KEY);
        public static final String TOP_MOVIE_URL=TOP_MOVIE_BASE_URL.concat(MOVIE_DB_KEY);
        public static final String MOVIE_POSTER_PATH="http://image.tmdb.org/t/p/w342/";
        public static final String MOVIE_VIDEO_APPEND_PATH="?api_key="+MOVIE_DB_KEY+"&append_to_response=videos";
        public static final String MOVIE_REVIEW_APPEND_PATH="/reviews?api_key="+MOVIE_DB_KEY+"&language=en-US&page=1";

    }
}
