package com.attilakasza.popularmovies;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.attilakasza.popularmovies.adapters.MovieAdapter;
import com.attilakasza.popularmovies.models.Movie;
import com.attilakasza.popularmovies.utilities.JsonUtils;
import com.attilakasza.popularmovies.utilities.NetworkUtils;

import java.net.URL;

public class PosterFragment extends Fragment implements MovieAdapter.OnItemClickListener {

    public PosterFragment() {}

    private Movie[] mMovie;
    private String mMovieType;
    private RecyclerView mMovieRecycler;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_poster, container, false);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        mMovieRecycler = rootView.findViewById(R.id.rv_poster);
        mMovieRecycler.setHasFixedSize(true);
        mMovieRecycler.setLayoutManager(layoutManager);

        mMovieType = "popular";
        new MovieQueryTask().execute(mMovieType);

        return rootView;
    }

    @Override
    public void onItemClick(int position) {

    }

    private class MovieQueryTask extends AsyncTask<String, Void, Movie[]> {

        @Override
        protected Movie[] doInBackground(String... movieType) {

            URL movieUrl = NetworkUtils.buildUrl(movieType[0]);

            try {
                String responseFromHttpUrl = NetworkUtils.getResponseFromHttpUrl(movieUrl);
                mMovie = JsonUtils.parseMovieJson(responseFromHttpUrl);
                return mMovie;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Movie[] movies) {
            MovieAdapter movieAdapter = new MovieAdapter(movies, getContext(), PosterFragment.this);
            mMovieRecycler.setAdapter(movieAdapter);
        }
    }
}
