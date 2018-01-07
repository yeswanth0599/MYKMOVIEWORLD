package com.mykmovies.android.mykmovieworld;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.mykmovies.android.mykmovieworld.data.TrailerAdapter;
import com.mykmovies.android.mykmovieworld.data.TrailerList;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * once user click the Recycle View Detailed information will display in screen
 * Activity: MovieDetailsDisplay Activity
 */
public class MovieDetailsDisplay extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>{
    private ImageView moviePosterDisplay;
    private TextView movieNameDisplay;
    private TextView movieOverViewDisplay;
    private TextView movieRatingDisplay;
    private TextView movieReleaseDateDisplay;
    private String   moviePosterSrc;
    private TextView movieTrailerSrc;
    private TextView movieTrailerLable;
    Context context;
    private ArrayList<TrailerList> trailerListMain;
    private TrailerAdapter trailerAdapter;
    private static int ADDRESSLOADER_ID=99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details_display);
        /**
         *  List of Display items
         *  1. Movie Poster
         *  2. Movie Original Title
         *  3. Movie Overview
         *  4. Movie Rating
         *  5. Movie Release Date
         *
         */
        trailerListMain=new ArrayList<TrailerList>();
        moviePosterDisplay=(ImageView)findViewById(R.id.moviePosterDisplay);
        movieNameDisplay=(TextView)findViewById(R.id.movieNameDisplay);
        movieOverViewDisplay=(TextView)findViewById(R.id.movieOverviewDisplay);
        movieRatingDisplay=(TextView)findViewById(R.id.movieRatingDisplay);
        movieReleaseDateDisplay=(TextView)findViewById(R.id.movieRleaseDateDisplay);
        movieTrailerLable=(TextView)findViewById(R.id.movie_trailer_lable);
        movieTrailerSrc=(TextView)findViewById(R.id.movie_trailer);
        moviePosterSrc=getIntent().getStringExtra("movie_Poster_ID");
        String movieTrailerUrl=getIntent().getStringExtra("movie_Trailer");
        /**
         * Get details from the MovieAdapter class and Displaying Movie Details
         *  1. Movie Poster
         *  2. Movie Original Title
         *  3. Movie Overview
         *  4. Movie Rating
         *  5. Movie Release Date
         */
        Bundle bundle = new Bundle();
        bundle.putString("url",movieTrailerUrl);
        getSupportLoaderManager().initLoader(ADDRESSLOADER_ID, bundle,this);
        Picasso.with(this)
                .load(moviePosterSrc)
                .into(moviePosterDisplay);
        movieNameDisplay.setText(getIntent().getStringExtra("movie_Original_Title"));
        movieOverViewDisplay.setText(getIntent().getStringExtra("movie_Synopsis"));
        movieRatingDisplay.setText(getIntent().getStringExtra("movie_User_Rating")+"%");
        movieReleaseDateDisplay.setText(getIntent().getStringExtra("movie_Release_Date"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        if(id == 99) {

            return new JsonLoader(this, args.getString("url"));
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        if (data != null) {

            try {

                JSONObject jsonObject=new JSONObject(data);
                JSONArray jsonArray = jsonObject.getJSONArray("results");
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject innerJsonObject = jsonArray.getJSONObject(i);
                    trailerListMain.add(new TrailerList(innerJsonObject.getString("key")));
                    trailerAdapter=new TrailerAdapter(this,trailerListMain);
                }
            } catch (JSONException e) {
                Log.d("onLoadFinished","JSONのパースに失敗しました。 JSONException=" + e);
            }


        } else{
            Log.d("onLoadFinished", "onLoadFinished error!");
        }
            }

    @Override
    public void onLoaderReset(Loader<String> loader) {
    }

}
