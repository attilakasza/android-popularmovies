package com.attilakasza.popularmovies.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {

    private String mId;
    private String mTitle;
    private String mDate;
    private String mPoster;
    private String mBackdrop;
    private String mVote;
    private String mPlotSynopsis;
    private boolean mFavorite;


    public Movie(String id, String title, String date, String poster, String backdrop, String vote, String plot, boolean favorite) {
        mId = id;
        mTitle = title;
        mDate = date;
        mPoster = poster;
        mBackdrop = backdrop;
        mVote = vote;
        mPlotSynopsis = plot;
        mFavorite = favorite;
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

    public String getmBackdrop() {
        return mBackdrop;
    }

    public void setmBackdrop(String mBackdrop) {
        this.mBackdrop = mBackdrop;
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

    public boolean ismFavorite() {
        return mFavorite;
    }

    public void setmFavorite(boolean mFavorite) {
        this.mFavorite = mFavorite;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mId);
        dest.writeString(this.mTitle);
        dest.writeString(this.mDate);
        dest.writeString(this.mPoster);
        dest.writeString(this.mBackdrop);
        dest.writeString(this.mVote);
        dest.writeString(this.mPlotSynopsis);
        dest.writeByte(this.mFavorite ? (byte) 1 : (byte) 0);
    }

    protected Movie(Parcel in) {
        this.mId = in.readString();
        this.mTitle = in.readString();
        this.mDate = in.readString();
        this.mPoster = in.readString();
        this.mBackdrop = in.readString();
        this.mVote = in.readString();
        this.mPlotSynopsis = in.readString();
        this.mFavorite = in.readByte() != 0;
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
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