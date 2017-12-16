package com.mykmovies.android.mykmovieworld;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
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
        MovieList movieList=movieListItems.get(position);
        holder.textView.setText(movieList.getMovieName());
        Picasso.with(context)
                .load(movieList.getMoviePoster())
                .into(holder.imageView);


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
        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            imageView=(ImageView)itemView.findViewById(R.id.movie_poster);
            textView=(TextView)itemView.findViewById(R.id.movieName);
        }

        @Override
        public void onClick(View view) {
            int position=getAdapterPosition();
            MovieList movieList=movieListItems.get(position);
            Intent intent=new Intent(context,MovieDetailsDisplay.class);
            intent.putExtra("movie_Poster_ID",movieList.getMoviePoster());
            intent.putExtra("movie_Original_Title",movieList.getMovieOriginalTitle());
            intent.putExtra("movie_Synopsis",movieList.getPlotSynopsis());
            intent.putExtra("movie_User_Rating",movieList.getUserRating());
            intent.putExtra("movie_Release_Date",movieList.getRelease_Date());
            context.startActivity(intent);
        }
    }
}