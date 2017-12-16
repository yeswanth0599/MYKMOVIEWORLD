package com.mykmovies.android.mykmovieworld;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * once user click the Recycle View Detailed information will display in screen
 * Activity: MovieDetailsDisplay Activity
 */
public class MovieDetailsDisplay extends AppCompatActivity {
    private ImageView moviePosterDisplay;
    private TextView movieNameDisplay;
    private TextView movieOverViewDisplay;
    private TextView movieRatingDisplay;
    private TextView movieReleaseDateDisplay;
    private String moviePosterSrc;

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
        moviePosterDisplay=(ImageView)findViewById(R.id.moviePosterDisplay);
        movieNameDisplay=(TextView)findViewById(R.id.movieNameDisplay);
        movieOverViewDisplay=(TextView)findViewById(R.id.movieOverviewDisplay);
        movieRatingDisplay=(TextView)findViewById(R.id.movieRatingDisplay);
        movieReleaseDateDisplay=(TextView)findViewById(R.id.movieRleaseDateDisplay);
        moviePosterSrc=getIntent().getStringExtra("movie_Poster_ID");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Picasso.with(this)
                .load(moviePosterSrc)
                .into(moviePosterDisplay);
        movieNameDisplay.setText(getIntent().getStringExtra("movie_Original_Title"));
        movieOverViewDisplay.setText(getIntent().getStringExtra("movie_Synopsis"));
        movieRatingDisplay.setText(getIntent().getStringExtra("movie_User_Rating")+"%");
        movieReleaseDateDisplay.setText(getIntent().getStringExtra("movie_Release_Date"));
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
}
