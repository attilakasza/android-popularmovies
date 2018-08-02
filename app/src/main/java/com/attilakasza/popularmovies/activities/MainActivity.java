package com.attilakasza.popularmovies.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.attilakasza.popularmovies.PopularMovies;
import com.attilakasza.popularmovies.R;
import com.attilakasza.popularmovies.adapters.FavoriteAdapter;
import com.attilakasza.popularmovies.adapters.MovieAdapter;
import com.attilakasza.popularmovies.data.FavoriteContract;
import com.attilakasza.popularmovies.fragments.MovieFragment;
import com.attilakasza.popularmovies.models.Movie;
import com.attilakasza.popularmovies.utilities.ConnectivityReceiver;
import com.attilakasza.popularmovies.utilities.JsonUtils;
import com.attilakasza.popularmovies.utilities.NetworkUtils;

import java.net.URL;

public class MainActivity extends AppCompatActivity implements MovieAdapter.OnItemClickListener, LoaderManager.LoaderCallbacks<Cursor>, FavoriteAdapter.OnItemClickListener,ConnectivityReceiver.ConnectivityReceiverListener {

    private static final String MOVIE = "MOVIE";
    private static final String MOVIE_QUERY = "MOVIE_QUERY";
    private static final int FAVORITE_LOADER_ID = 9;
    private Movie[] mMovie;
    private RecyclerView mMovieRecycler;
    private String mMovieType;
    private boolean mTwoPane;
    private FavoriteAdapter mAdapter;

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

        mAdapter = new FavoriteAdapter(MainActivity.this, MainActivity.this);

        if (checkConnection()) {
            if (savedInstanceState != null && savedInstanceState.containsKey(MOVIE_QUERY)) {
                mMovieType = savedInstanceState.getString(MOVIE_QUERY);
                if (mMovieType.equals("favorite")) {
                    mMovieRecycler.setAdapter(mAdapter);
                    getSupportLoaderManager().initLoader(FAVORITE_LOADER_ID, null, this);
                } else {
                    new MovieQueryTask().execute(mMovieType);
                }
            } else {
                mMovieType = "popular";
                new MovieQueryTask().execute(mMovieType);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(FAVORITE_LOADER_ID, null, this);

        // register connection status listener
        PopularMovies.getInstance().setConnectivityListener(this);
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
        if (checkConnection()) {
            if (id == R.id.popular) {
                mMovieType = "popular";
                new MovieQueryTask().execute(mMovieType);
            }
            if (id == R.id.top_rated) {
                mMovieType = "top_rated";
                new MovieQueryTask().execute(mMovieType);
            }
        }
        if (id == R.id.favorite) {
            mMovieType = "favorite";
            mAdapter = new FavoriteAdapter(MainActivity.this, MainActivity.this);
            mMovieRecycler.setAdapter(mAdapter);
            getSupportLoaderManager().restartLoader(FAVORITE_LOADER_ID, null, this);
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

    @SuppressLint("StaticFieldLeak")
    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new AsyncTaskLoader<Cursor>(this) {

            Cursor mTaskData = null;

            @Override
            protected void onStartLoading() {
                if (mTaskData != null) {
                    deliverResult(mTaskData);
                } else {
                    forceLoad();
                }
            }

            @Override
            public Cursor loadInBackground() {
                try {
                    return getContentResolver().query(FavoriteContract.FavoriteEntry.CONTENT_URI,
                            null,
                            null,
                            null,
                            null);

                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            public void deliverResult(Cursor data) {
                mTaskData = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

    @Override
    public void onClick(Movie movie) {
        if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putParcelable(MOVIE, movie);
            Fragment fragment = new MovieFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movie_container, fragment)
                    .commit();
        } else {
            Intent intent = new Intent(getApplicationContext(), MovieActivity.class);
            intent.putExtra(MOVIE, movie);
            startActivity(intent);
        }
    }

    // Method to manually check connection status
    private boolean checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showSnack(isConnected);
        return  isConnected;
    }

    // Showing the status in Snackbar
    private void showSnack(boolean isConnected) {
        if (!isConnected) {
            Snackbar snackbar = Snackbar
                    .make(findViewById(R.id.poster_container), "Sorry! Not connected to internet", Snackbar.LENGTH_LONG);

            View sbView = snackbar.getView();
            TextView textView = sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(getResources().getColor(R.color.colorSnackbar));
            snackbar.show();
        }
    }

    /**
     * Callback will be triggered when there is change in
     * network connection
     */
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
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
