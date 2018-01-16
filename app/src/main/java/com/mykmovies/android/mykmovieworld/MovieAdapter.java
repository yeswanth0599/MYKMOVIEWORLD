package com.mykmovies.android.mykmovieworld;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.content.ContentValues;
import android.net.Uri;

import com.mykmovies.android.mykmovieworld.data.MovieContract;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by yeswa on 01-12-2017.
 */

/**
 * MovieAdapter class extending RecyclerView
 *Creating ViewHolder to hold the views
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private List<MovieList> movieListItems;
    private Context context;
    public MovieAdapter(List<MovieList> movieListItems, Context context) {
        this.movieListItems = movieListItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /**
         * inflating the MovieList.xml in MainActivity
         */
        View viewHolderRefrence= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movielist,parent,false);
        /**
         * Return ViewHolder Object
         */
        return new ViewHolder(viewHolderRefrence);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final MovieList movieList=movieListItems.get(position);
        holder.textView.setText(movieList.getMovieName());
        Picasso.with(context)
                .load(movieList.getMoviePoster())
                .into(holder.imageView);

        holder.toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ContentValues contentValues=new ContentValues();
                    contentValues.put(MovieContract.MovieEntryInfo.COLUMN_MOVIE_ID,movieList.getMovieID());
                    contentValues.put(MovieContract.MovieEntryInfo.COLUMN_MOVIE_TITLE,movieList.getMovieName());
                    contentValues.put(MovieContract.MovieEntryInfo.COLUMN_MOVIE_POSTER_URL,movieList.getMoviePoster());
                    contentValues.put(MovieContract.MovieEntryInfo.COLUMN_MOVIE_SYNOPSIS,movieList.getPlotSynopsis());
                    contentValues.put(MovieContract.MovieEntryInfo.COLUMN_MOVIE_RATING,movieList.getUserRating());
                    contentValues.put(MovieContract.MovieEntryInfo.COLUMN_MOVIE_RELEASE_DATE,movieList.getRelease_Date());
                    contentValues.put(MovieContract.MovieEntryInfo.COLUMN_MOVIE_ORIGINAL_TITLE,movieList.getMovieOriginalTitle());
                    contentValues.put(MovieContract.MovieEntryInfo.COLUMN_MOVIE_TRAILER,movieList.getMovieTrailer());
                    Uri uri=context.getContentResolver().insert(MovieContract.MovieEntryInfo.CONTENT_URI,contentValues);

                    if(uri!=null)
                    {
                       Toast.makeText(context,movieList.getMovieName()+"Added to Favorites",Toast.LENGTH_LONG).show();
                    }
                } else {
                    // The toggle is disabled
                    Uri uri = MovieContract.MovieEntryInfo.CONTENT_URI;
                    uri = uri.buildUpon().appendPath(movieList.getMovieID()).build();
                    int deleteCheck=context.getContentResolver().delete(uri, null, null);
                    // COMPLETED (2) Delete a single row of data using a ContentResolver
                    if(deleteCheck==1)
                    {
                        Toast.makeText(context,movieList.getMovieName()+"Removed from Favorites",Toast.LENGTH_LONG).show();
                    }

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return movieListItems.size();
    }

    /**
     * Creating ViewHolder(InnerClass of MovieAdapter) class
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView imageView;
        public TextView textView;
        public ToggleButton toggleButton;
        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            imageView=(ImageView)itemView.findViewById(R.id.movie_poster);
            textView=(TextView)itemView.findViewById(R.id.movieName);
            toggleButton=(ToggleButton)itemView.findViewById(R.id.movie_toggle);

        }

        @Override
        public void onClick(View view) {
            int position=getAdapterPosition();
            MovieList movieList=movieListItems.get(position);
            /**
             * Movie Toggle OnClick Implementation
             */
            Intent intent=new Intent(context,MovieDetailsDisplay.class);
            intent.putExtra("movie_Poster_ID",movieList.getMoviePoster());
            intent.putExtra("movie_Original_Title",movieList.getMovieOriginalTitle());
            intent.putExtra("movie_Synopsis",movieList.getPlotSynopsis());
            intent.putExtra("movie_User_Rating",movieList.getUserRating());
            intent.putExtra("movie_Release_Date",movieList.getRelease_Date());
            intent.putExtra("movie_Trailer",movieList.getMovieTrailer());
            context.startActivity(intent);
        }

    }
}