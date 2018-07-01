package com.attilakasza.popularmovies.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.attilakasza.popularmovies.R;
import com.attilakasza.popularmovies.models.Movie;

import butterknife.ButterKnife;

public class ReviewFragment extends Fragment {

    private Movie mMovie;
    private static final String MOVIE = "MOVIE";


    public ReviewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_review, container, false);
        ButterKnife.bind(this, rootView);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mMovie = bundle.getParcelable(MOVIE);
        }

        return inflater.inflate(R.layout.fragment_review, container, false);
    }


}
