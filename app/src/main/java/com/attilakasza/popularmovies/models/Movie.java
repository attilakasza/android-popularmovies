package com.attilakasza.popularmovies.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {

    private String mTitle;
    private String mDate;
    private String mPoster;
    private String mVote;
    private String mPlotSynopsis;


    public Movie(String title, String date, String poster, String vote, String plot) {
        mTitle = title;
        mDate = date;
        mPoster = poster;
        mVote = vote;
        mPlotSynopsis = plot;
    }


    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    public String getmPoster() {
        return mPoster;
    }

    public void setmPoster(String mPoster) {
        this.mPoster = mPoster;
    }

    public String getmVote() {
        return mVote;
    }

    public void setmVote(String mVote) {
        this.mVote = mVote;
    }

    public String getmPlotSynopsis() {
        return mPlotSynopsis;
    }

    public void setmPlotSynopsis(String mPlotSynopsis) {
        this.mPlotSynopsis = mPlotSynopsis;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mTitle);
        dest.writeString(this.mDate);
        dest.writeString(this.mPoster);
        dest.writeString(this.mVote);
        dest.writeString(this.mPlotSynopsis);
    }

    protected Movie(Parcel in) {
        this.mTitle = in.readString();
        this.mDate = in.readString();
        this.mPoster = in.readString();
        this.mVote = in.readString();
        this.mPlotSynopsis = in.readString();
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}