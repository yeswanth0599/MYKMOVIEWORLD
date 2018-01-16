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
 * Created by yeswa on 08-01-2018.
 */

public class ReviewAdapter extends ArrayAdapter<ReviewList> {

    public ReviewAdapter(Activity context, ArrayList<ReviewList> reviewLists) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context,0,reviewLists);
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.review_list_display,parent,false);
        }
        ReviewList reviewList = getItem(position);
        TextView textViewAuthor=(TextView)listItemView.findViewById(R.id.author_name_display);
        textViewAuthor.setText(reviewList.getReviewAuthor());
        TextView textViewReviewContent=(TextView)listItemView.findViewById(R.id.review_content);
        textViewReviewContent.setText(reviewList.getReviewContent());
        return listItemView;
    }
}
