package com.mykmovies.android.mykmovieworld;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
 * Most Popular movies Fragment will display Most popular movies from TMDB
 *
 */
public class MostPopular extends Fragment implements LoaderManager.LoaderCallbacks<String> {
    /**
     * RecyclerView , custom List (LIST<MovieList>) variables created
     */
    private RecyclerView movieRecycleViewMain;
    private RecyclerView.Adapter movieAdapterMain;
    private List<MovieList> movieListsMain;
    private ProgressDialog progressDialog;
    private String MOVIEDB_URL;
    private String movieDBKey;
    String urlResult;
    private static int ADDRESSLOADER_ID=99;
    /**
     * Using baseURL fetch the popular movies from the TMDB
     */
    private String baseURL="https://api.themoviedb.org/3/movie/popular?api_key=";

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_most_popular, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /**
         * Accessing Movie DB Key from the Gradle File
         * combined MoviedDBKey with base url will get popular movie information
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
        progressDialog=new ProgressDialog(getContext());
        progressDialog.setMessage("Loading Data...");
        progressDialog.show();
        Bundle bundle = new Bundle();
        bundle.putString("url", MOVIEDB_URL);
        getLoaderManager().initLoader(ADDRESSLOADER_ID, bundle, this);
}

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        if(id == 99) {

            return new JsonLoader(getContext(), args.getString("url"));
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        progressDialog.dismiss();
        if (data != null) {

            try {

                JSONObject jsonObject=new JSONObject(data);
                JSONArray jsonArray = jsonObject.getJSONArray("results");

                for (int i = 0; i < jsonArray.length(); i++) {

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
                Log.d("onLoadFinished","JSONのパースに失敗しました。 JSONException=" + e);
            }


        }else{
            Log.d("onLoadFinished", "onLoadFinished error!");
        }
    }


    @Override
    public void onLoaderReset(Loader<String> loader) {

    }




/*
    @Override
    public void onLoadFinished(Loader<JSONObject> loader, JSONObject data) {
        progressDialog.dismiss();
        if (data != null) {

            try {

                JSONObject jsonObject=new JSONObject(data.toString());
                JSONArray jsonArray = jsonObject.getJSONArray("results");

                for (int i = 0; i < jsonArray.length(); i++) {

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
                Log.d("onLoadFinished","JSONのパースに失敗しました。 JSONException=" + e);
            }


        }else{
            Log.d("onLoadFinished", "onLoadFinished error!");
        }
    }

    public void onLoaderReset(Loader<JSONObject> loader) {
        // TODO 自動生成されたメソッド・スタブ

    }*/
}