package com.attilakasza.popularmovies.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.attilakasza.popularmovies.R;
import com.attilakasza.popularmovies.adapters.MovieAdapter;
import com.attilakasza.popularmovies.fragments.MovieFragment;
import com.attilakasza.popularmovies.models.Movie;
import com.attilakasza.popularmovies.utilities.JsonUtils;
import com.attilakasza.popularmovies.utilities.NetworkUtils;

import java.net.URL;

public class MainActivity extends AppCompatActivity implements MovieAdapter.OnItemClickListener {

    private Movie[] mMovie;
    private RecyclerView mMovieRecycler;
    private String mMovieType;
    private boolean mTwoPane;
    private static final String MOVIE = "MOVIE";
    private static final String MOVIE_QUERY = "MOVIE_QUERY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        if (findViewById(R.id.movie_container) != null) {
            mTwoPane = true;
        }

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        mMovieRecycler = findViewById(R.id.rv_poster);
        mMovieRecycler.setHasFixedSize(true);
        mMovieRecycler.setLayoutManager(layoutManager);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(MOVIE_QUERY)) {
                mMovieType = savedInstanceState.getString(MOVIE_QUERY);
            }
        } else {
            mMovieType = "popular";
        }
        new MovieQueryTask().execute(mMovieType);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String savedType = mMovieType;
        outState.putString(MOVIE_QUERY, savedType);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.popular) {
            mMovieType = "popular";
            new MovieQueryTask().execute(mMovieType);
        }
        if (id == R.id.top_rated) {
            mMovieType = "top_rated";
            new MovieQueryTask().execute(mMovieType);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(int position) {
        if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putParcelable(MOVIE, mMovie[position]);
            Fragment fragment = new MovieFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movie_container, fragment)
                    .commit();
        } else {
            Intent intent = new Intent(getApplicationContext(), MovieActivity.class);
            intent.putExtra(MOVIE, mMovie[position]);
            startActivity(intent);
        }
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
            MovieAdapter movieAdapter = new MovieAdapter(movies, MainActivity.this, MainActivity.this);
            mMovieRecycler.setAdapter(movieAdapter);
        }
    }
}
