package com.attilakasza.popularmovies.models;

public class Movie {

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

}