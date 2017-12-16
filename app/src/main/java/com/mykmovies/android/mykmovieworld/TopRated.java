package com.mykmovies.android.mykmovieworld;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


/**
 * Top Rated movies Fragment will display Most popular movies from TMDB
 *
 */
public class TopRated extends Fragment {
    /**
     * RecyclerView , custom List (LIST<MovieList>) variables created
     */
    private RecyclerView movieRecycleViewMain;
    private RecyclerView.Adapter movieAdapterMain;
    private List<MovieList> movieListsMain;
    private ProgressDialog progressDialog;
    private String MOVIEDB_URL;
    private String movieDBKey;
    private String urlResult;
    /**
     * Using baseURL fetch the Top Rated movies from the TMDB
     */
    private String baseURL="https://api.themoviedb.org/3/movie/top_rated?api_key=";
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_top_rated, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /**
         * Accessing Movie DB Key from the Gradle File
         * combined MoviedDBKey with base url will get Top Rated movies information
         */
        movieDBKey= BuildConfig.MovieDBApiKeyInfo;
        MOVIEDB_URL=baseURL+movieDBKey;
        movieRecycleViewMain=(RecyclerView)getActivity().findViewById(R.id.movie_recyclerview);
        movieRecycleViewMain.setHasFixedSize(true);
        movieRecycleViewMain.setLayoutManager(new GridLayoutManager(getContext(),2));
        movieListsMain=new ArrayList<>();
        /**
         * Fetching JSON Data from the AsyncTask Loader
         * AsyncTask implemented in JsonAsyncTask Class
         */
        try {
            urlResult=new JsonAsyncTask().execute(MOVIEDB_URL).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        progressDialog=new ProgressDialog(getContext());
        progressDialog.setMessage("Loading Data...");
        progressDialog.show();
        displayView(urlResult);
    }
    /**
     * Displaying the URL Result in the screen
     * @param urlResult
     * urlResult will fetch from the JsonAsyncTask Class
     */
    private void displayView(String urlResult)
    {
        progressDialog.dismiss();
        try {
            JSONObject mainJsonObject=new JSONObject(urlResult);
            JSONArray jsonArray =  mainJsonObject.getJSONArray("results");
            for(int i=0;i<jsonArray.length();i++)
            {
                JSONObject innerJsonObject=jsonArray.getJSONObject(i);
                String moviePoster=innerJsonObject.getString("poster_path");
                String moviePosterURL="http://image.tmdb.org/t/p/w342/"+moviePoster;
                String movieTitle=innerJsonObject.getString("title");
                String movieOriginalTitle=innerJsonObject.getString("original_title");
                String movieSynopsis=innerJsonObject.getString("overview");
                String movieRating=innerJsonObject.getString("vote_average");
                String movieReleaseDate=innerJsonObject.getString("release_date");
                MovieList movieList=new MovieList(movieTitle,moviePosterURL,movieSynopsis,movieRating,movieReleaseDate,movieOriginalTitle);
                movieListsMain.add(movieList);
                movieAdapterMain=new MovieAdapter(movieListsMain,getContext());
                movieRecycleViewMain.setAdapter(movieAdapterMain);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
