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
import com.attilakasza.popularmovies.adapters.ReviewAdapter;
import com.attilakasza.popularmovies.models.Movie;
import com.attilakasza.popularmovies.models.Review;
import com.attilakasza.popularmovies.utilities.JsonUtils;
import com.attilakasza.popularmovies.utilities.NetworkUtils;

import java.net.URL;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewFragment extends Fragment {

    private static final String MOVIE = "MOVIE";
    private static final int REVIEW_LOADER_ID = 1;
    private Movie mMovie;
    private ReviewAdapter mReviewAdapter;
    @BindView(R.id.rv_review) RecyclerView mReviewRecycler;

    public ReviewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_review, container, false);
        ButterKnife.bind(this, rootView);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mMovie = bundle.getParcelable(MOVIE);
        }

        mReviewRecycler.setHasFixedSize(true);
        mReviewRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mReviewAdapter = new ReviewAdapter(getActivity());
        mReviewRecycler.setAdapter(mReviewAdapter);
        Objects.requireNonNull(getActivity()).getSupportLoaderManager().initLoader(REVIEW_LOADER_ID, null, new ReviewFragment.ReviewLoad());

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(getActivity()).getSupportLoaderManager().restartLoader(REVIEW_LOADER_ID, null, new ReviewFragment.ReviewLoad());
    }

    private class ReviewLoad implements LoaderManager.LoaderCallbacks<Review[]> {

        @NonNull
        @SuppressLint("StaticFieldLeak")
        @Override
        public Loader<Review[]> onCreateLoader(int id, final Bundle loaderArgs) {

            return new AsyncTaskLoader<Review[]>(Objects.requireNonNull(getContext())) {

                Review[] mReview = null;

                @Override
                protected void onStartLoading() {
                    if (mReview != null) {
                        deliverResult(mReview);
                    } else {
                        forceLoad();
                    }
                }

                @Override
                public Review[] loadInBackground() {

                    URL reviewUrl = NetworkUtils.buildReviewUrl(mMovie.getmId());

                    try {
                        String responseFromHttpUrl = NetworkUtils.getResponseFromHttpUrl(reviewUrl);
                        mReview = JsonUtils.parseReviewJson(responseFromHttpUrl);
                        return mReview;

                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                }

                public void deliverResult(Review[] data) {
                    mReview = data;
                    super.deliverResult(data);
                }
            };
        }

        @Override
        public void onLoadFinished(@NonNull Loader<Review[]> loader, Review[] data) {
            mReviewAdapter.swapReview(data);
        }

        @Override
        public void onLoaderReset(@NonNull Loader<Review[]> loader) {
            mReviewAdapter.swapReview(null);
        }
    }
}
