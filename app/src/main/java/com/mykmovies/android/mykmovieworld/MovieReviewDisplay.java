package com.mykmovies.android.mykmovieworld;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;

import com.mykmovies.android.mykmovieworld.data.MovieContract;
import com.mykmovies.android.mykmovieworld.data.ReviewAdapter;
import com.mykmovies.android.mykmovieworld.data.ReviewList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MovieReviewDisplay extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
private String movieReviewPath;
private String movieReviewUrl;
private static int ADDRESSLOADER_ID=99;
private ArrayList<ReviewList> reviewListMain;
private ReviewAdapter reviewAdapter;
private Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_review_display);
        reviewListMain=new ArrayList<ReviewList>();
        Intent intent = getIntent();
        movieReviewPath= intent.getStringExtra("movie_review_path");
        movieReviewUrl=movieReviewPath.concat(MovieContract.MovieEntryInfo.MOVIE_REVIEW_APPEND_PATH);
        bundle= new Bundle();
        bundle.putString("url",movieReviewUrl);
        getSupportLoaderManager().initLoader(ADDRESSLOADER_ID, bundle,this);
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
                    reviewListMain.add(new ReviewList(innerJsonObject.getString("author"),innerJsonObject.getString("content")));

                }
                // movieTrailerDisplay.setText(trailerListMain.toString());
                /**
                 *

                 arrayAdapter=new ArrayAdapter<String>(getApplicationContext(),R.layout.trailer_list_display,R.id.movie_trailer,trailerListMain);
                 trailerListDisplay.setAdapter(arrayAdapter);
                 */
                reviewAdapter = new ReviewAdapter(this, reviewListMain);
                ListView listView = (ListView) findViewById(R.id.movie_review_list_display);
                listView.setAdapter(reviewAdapter);

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
