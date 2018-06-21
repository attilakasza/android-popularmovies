package com.attilakasza.popularmovies.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

public class MovieFragment extends Fragment {

    private Movie mMovie;
    private static final String MOVIE = "MOVIE";
    private static final String POSTER_URL = "http://image.tmdb.org/t/p/w185";

    @BindView(R.id.tv_title) TextView textTitle ;
    @BindView(R.id.tv_date) TextView textDate;
    @BindView(R.id.iv_poster) ImageView imagePoster;
    @BindView(R.id.tv_vote) TextView textVote ;
    @BindView(R.id.tv_plot) TextView textPlot ;

    public MovieFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie, container, false);
        ButterKnife.bind(this, rootView);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mMovie = bundle.getParcelable(MOVIE);
        }

        textTitle.setText(mMovie.getmTitle());
        textVote.setText(mMovie.getmVote());
        textPlot.setText(mMovie.getmPlotSynopsis());
        textDate.setText("(" + mMovie.getmDate().substring(0, 4) + ")");
        Picasso.with(getContext())
                .load(POSTER_URL.concat(mMovie.getmPoster()))
                .into(imagePoster);

        return rootView;
    }
}
