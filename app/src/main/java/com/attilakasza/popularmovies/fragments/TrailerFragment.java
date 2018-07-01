package com.attilakasza.popularmovies.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.attilakasza.popularmovies.R;
import com.attilakasza.popularmovies.models.Movie;

import butterknife.ButterKnife;

public class TrailerFragment extends Fragment {

    private Movie mMovie;
    private static final String MOVIE = "MOVIE";


    public TrailerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_trailer, container, false);
        ButterKnife.bind(this, rootView);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mMovie = bundle.getParcelable(MOVIE);
        }

        return rootView;
    }


}
