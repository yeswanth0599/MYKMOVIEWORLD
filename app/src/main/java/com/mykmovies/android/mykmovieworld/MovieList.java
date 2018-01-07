package com.mykmovies.android.mykmovieworld;

/**
 * Creating MovieList Getter Methods.
 */

public class MovieList {
    private String movieName;
    private String movieOriginalTitle;
    private String moviePoster;
    private String plotSynopsis;
    private String userRating;
    private String release_Date;
    private String movieTrailer;
    public MovieList(String movieName, String moviePoster,String plotSynopsis,String userRating,String release_Date,String movieOriginalTitle, String movieTrailer)
    {
        this.movieName=movieName;
        this.movieOriginalTitle=movieOriginalTitle;
        this.moviePoster=moviePoster;
        this.plotSynopsis=plotSynopsis;
        this.userRating=userRating;
        this.release_Date=release_Date;
        this.movieTrailer=movieTrailer;
    }

    public String getMovieName() {
        return movieName;
    }

    public String getMovieOriginalTitle() {
        return movieOriginalTitle;
    }

    public String getMoviePoster(){
        return moviePoster;
    }

    public String getPlotSynopsis() {
        return plotSynopsis;
    }

    public String getUserRating() {
        return userRating;
    }

    public String getRelease_Date() {
        return release_Date;
    }

    public String getMovieTrailer(){ return movieTrailer; }
}
