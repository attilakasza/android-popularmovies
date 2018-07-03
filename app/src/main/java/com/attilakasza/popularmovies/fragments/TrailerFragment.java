package com.attilakasza.popularmovies.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.attilakasza.popularmovies.R;
import com.attilakasza.popularmovies.adapters.TrailerAdapter;
import com.attilakasza.popularmovies.models.Movie;
import com.attilakasza.popularmovies.models.Trailer;
import com.attilakasza.popularmovies.utilities.JsonUtils;
import com.attilakasza.popularmovies.utilities.NetworkUtils;

import java.net.URL;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrailerFragment extends Fragment {

    private static final String MOVIE = "MOVIE";
    private static final int TRAILER_LOADER_ID = 2;
    private Movie mMovie;
    private TrailerAdapter mTrailerAdapter;
    @BindView(R.id.rv_trailer) RecyclerView mTrailerRecycler;

    public TrailerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_trailer, container, false);
        ButterKnife.bind(this, rootView);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mMovie = bundle.getParcelable(MOVIE);
        }

        mTrailerRecycler.setHasFixedSize(true);
        mTrailerRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mTrailerAdapter = new TrailerAdapter(getActivity());
        mTrailerRecycler.setAdapter(mTrailerAdapter);

        Objects.requireNonNull(getActivity()).getSupportLoaderManager().initLoader(TRAILER_LOADER_ID, null, new TrailerLoad());

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(getActivity()).getSupportLoaderManager().restartLoader(TRAILER_LOADER_ID, null, new TrailerFragment.TrailerLoad());

    }

    private class TrailerLoad implements LoaderManager.LoaderCallbacks<Trailer[]> {

        @NonNull
        @SuppressLint("StaticFieldLeak")
        @Override
        public Loader<Trailer[]> onCreateLoader(int id, final Bundle loaderArgs) {

            return new AsyncTaskLoader<Trailer[]>(Objects.requireNonNull(getContext())) {

                Trailer[] mTrailer = null;

                @Override
                protected void onStartLoading() {
                    if (mTrailer != null) {
                        deliverResult(mTrailer);
                    } else {
                        forceLoad();
                    }
                }

                @Override
                public Trailer[] loadInBackground() {

                    URL trailerUrl = NetworkUtils.buildTrailerUrl(mMovie.getmId());

                    try {
                        String responseFromHttpUrl = NetworkUtils.getResponseFromHttpUrl(trailerUrl);
                        mTrailer = JsonUtils.parseTrailerJson(responseFromHttpUrl);
                        return mTrailer;

                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                }

                public void deliverResult(Trailer[] data) {
                    mTrailer = data;
                    super.deliverResult(data);
                }
            };
        }

        @Override
        public void onLoadFinished(@NonNull Loader<Trailer[]> loader, Trailer[] data) {
            mTrailerAdapter.swapTrailer(data);
        }

        @Override
        public void onLoaderReset(@NonNull Loader<Trailer[]> loader) {
            mTrailerAdapter.swapTrailer(null);
        }
    }
}
