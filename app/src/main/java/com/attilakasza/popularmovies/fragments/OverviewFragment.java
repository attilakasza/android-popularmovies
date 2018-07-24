package com.attilakasza.popularmovies.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.attilakasza.popularmovies.R;
import com.attilakasza.popularmovies.models.Movie;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OverviewFragment extends Fragment {

    private Movie mMovie;
    private static final String MOVIE = "MOVIE";
    private static final String POSTER_URL = "http://image.tmdb.org/t/p/w342";

    @BindView(R.id.iv_poster) ImageView mImageViewPoster;
    @BindView(R.id.tv_date) TextView mTextViewDate;
    @BindView(R.id.tv_vote) TextView mTextViewVote;
    @BindView(R.id.tv_plot) TextView mTextViewPlot;


    public OverviewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_overview, container, false);
        ButterKnife.bind(this, rootView);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mMovie = bundle.getParcelable(MOVIE);
        }

        mTextViewDate.setText(mMovie.getmDate().substring(0, 4));
        Picasso.with(getContext())
                .load(POSTER_URL.concat(mMovie.getmPoster()))
                .into(mImageViewPoster);
        mTextViewVote.setText(mMovie.getmVote());
        mTextViewPlot.setText(mMovie.getmPlotSynopsis());

        return rootView;
    }


}
