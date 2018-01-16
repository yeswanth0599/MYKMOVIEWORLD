package com.mykmovies.android.mykmovieworld;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeIntents;
import com.mykmovies.android.mykmovieworld.data.MovieContract;
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
    private ListView trailerListDisplay;
    private Button movieReviewButton;
    Context context;
    private ArrayList<TrailerList> trailerListMain;
    private TrailerAdapter trailerAdapter;
    private static int ADDRESSLOADER_ID=99;
    private Bundle bundle;
    //private TextView movieTrailerDisplay;

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
        trailerListDisplay=(ListView)findViewById(R.id.trailer_list_display);
        //movieTrailerDisplay=(TextView)findViewById(R.id.movie_trailer_display);
        moviePosterSrc=getIntent().getStringExtra("movie_Poster_ID");
        movieReviewButton=(Button)findViewById(R.id.movie_review_button);
        movieReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MovieDetailsDisplay.this, MovieReviewDisplay.class);
                myIntent.putExtra("movie_review_path", getIntent().getStringExtra("movie_Trailer")); //Optional parameters
                startActivity(myIntent);
            }
        });
        /**
         * Get details from the MovieAdapter class and Displaying Movie Details
         *  1. Movie Poster
         *  2. Movie Original Title
         *  3. Movie Overview
         *  4. Movie Rating
         *  5. Movie Release Date
         */
        bundle= new Bundle();
        bundle.putString("url",getIntent().getStringExtra("movie_Trailer")+ MovieContract.MovieEntryInfo.MOVIE_VIDEO_APPEND_PATH);
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
                JSONObject subJsonObject=jsonObject.getJSONObject("videos");
                JSONArray jsonArray = subJsonObject.getJSONArray("results");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject innerJsonObject = jsonArray.getJSONObject(i);
                        trailerListMain.add(new TrailerList(innerJsonObject.getString("name"),innerJsonObject.getString("key")));

                    }
                   // movieTrailerDisplay.setText(trailerListMain.toString());
                /**
                 *

                arrayAdapter=new ArrayAdapter<String>(getApplicationContext(),R.layout.trailer_list_display,R.id.movie_trailer,trailerListMain);
                    trailerListDisplay.setAdapter(arrayAdapter);
                 */
                    trailerAdapter = new TrailerAdapter(this, trailerListMain);
                    ListView listView = (ListView) findViewById(R.id.trailer_list_display);
                    listView.setAdapter(trailerAdapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            TrailerList trailerList = trailerListMain.get(position);
                            Intent intentStartYoutube =
                                    YouTubeIntents.createPlayVideoIntent(getApplicationContext(),trailerList.getYoutubeKey());
                            startActivity(intentStartYoutube);
                        }
                    });



            } catch (JSONException e) {
                Log.d("onLoadFinished","JSON parsing is failed。 JSONException=" + e);
            }


        } else{
            Log.d("onLoadFinished", "onLoadFinished error!");
        }

            }

    @Override
    public void onLoaderReset(Loader<String> loader) {
        getSupportLoaderManager().initLoader(ADDRESSLOADER_ID, bundle,this);
    }

}
