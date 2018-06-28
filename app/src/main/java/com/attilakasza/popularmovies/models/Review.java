package com.attilakasza.popularmovies.models;

import com.google.gson.annotations.SerializedName;

public class Review {

    @SerializedName("author")
    private String mAuthor;
    @SerializedName("content")
    private String mContent;

    public String getmAuthor() {
        return mAuthor;
    }

    public void setmAuthor(String mAuthor) {
        this.mAuthor = mAuthor;
    }

    public String getmContent() {
        return mContent;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent;
    }
}