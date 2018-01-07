package com.mykmovies.android.mykmovieworld.data;

import android.provider.BaseColumns;

import com.mykmovies.android.mykmovieworld.BuildConfig;

/**
 * Created by yeswa on 04-01-2018.
 */

public class MovieContract {

    public static final String MOVIE_BASE_URL="https://api.themoviedb.org/3/movie/";
    public static final String POPULAR_MOVIE_BASE_URL=MOVIE_BASE_URL.concat("popular?api_key=");
    public static final String TOP_MOVIE_BASE_URL=MOVIE_BASE_URL.concat("top_rated?api_key=");

    public static final class MovieEntryInfo implements BaseColumns
    {
        public static final String MOVIE_DB_KEY=BuildConfig.MovieDBApiKeyInfo;
        public static final String YOUTUBE_API_KEY=BuildConfig.YoutubeApiKeyInfo;
        public static final String POPULAR_MOVIE_URL=POPULAR_MOVIE_BASE_URL.concat(MOVIE_DB_KEY);
        public static final String TOP_MOVIE_URL=TOP_MOVIE_BASE_URL.concat(MOVIE_DB_KEY);
        public static final String MOVIE_POSTER_PATH="http://image.tmdb.org/t/p/w342/";

    }
}
