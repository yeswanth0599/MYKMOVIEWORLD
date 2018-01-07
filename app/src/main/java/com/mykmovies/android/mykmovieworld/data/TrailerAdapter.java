package com.mykmovies.android.mykmovieworld.data;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mykmovies.android.mykmovieworld.R;

import java.util.ArrayList;

/**
 * Created by yeswa on 06-01-2018.
 */

public class TrailerAdapter extends ArrayAdapter<TrailerList>{

    public TrailerAdapter(Activity context, ArrayList<TrailerList> trailerLists) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context,0,trailerLists);
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.activity_movie_details_display,parent,false);
        }
        TrailerList trailerList = getItem(position);
        TextView textViewTrailer=(TextView)listItemView.findViewById(R.id.movie_trailer);
        textViewTrailer.setText(trailerList.getmMovieTrailer().toString());
        return listItemView;
    }

}
