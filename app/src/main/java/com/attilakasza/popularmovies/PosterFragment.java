package com.attilakasza.popularmovies;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PosterFragment extends Fragment {

    public PosterFragment() {}

    private RecyclerView mMovieRecycler;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_poster, container, false);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        mMovieRecycler = rootView.findViewById(R.id.rv_poster);
        mMovieRecycler.setHasFixedSize(true);
        mMovieRecycler.setLayoutManager(layoutManager);

        return rootView;
    }
}
