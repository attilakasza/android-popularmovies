package com.attilakasza.popularmovies.models;

import com.google.gson.annotations.SerializedName;

public class Trailer {

    @SerializedName("name")
    private String mName;
    @SerializedName("key")
    private String mKey;

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmKey() {
        return mKey;
    }

    public void setmKey(String mKey) {
        this.mKey = mKey;
    }
}