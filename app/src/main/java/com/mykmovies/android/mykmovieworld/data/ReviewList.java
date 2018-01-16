package com.mykmovies.android.mykmovieworld.data;

/**
 * Created by yeswa on 08-01-2018.
 */

public class ReviewList {
    private String reviewAuthor;
    private String reviewContent;
    public ReviewList(String reviewAuthor,String reviewContent)
    {
        this.reviewAuthor=reviewAuthor;
        this.reviewContent=reviewContent;

    }

    public String getReviewAuthor() {
        return reviewAuthor;
    }

    public String getReviewContent() {
        return reviewContent;
    }
}
