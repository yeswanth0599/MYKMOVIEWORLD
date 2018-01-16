package com.mykmovies.android.mykmovieworld.data;

/**
 * Created by yeswa on 06-01-2018.
 */

public class TrailerList {
    private String movieTrailer;
    private String youtubeKey;
    public TrailerList(String movieTrailer,String youtubeKey)
    {
        this.movieTrailer=movieTrailer;
        this.youtubeKey=youtubeKey;

    }

    public String getmMovieTrailer() {
        return movieTrailer;
    }
    public String getYoutubeKey() { return youtubeKey; }
}
