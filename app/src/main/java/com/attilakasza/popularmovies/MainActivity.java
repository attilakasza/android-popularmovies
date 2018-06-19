package com.attilakasza.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private PosterFragment mPosterFragment;
    private static final String FRAGMENT_NAME = "POSTERFRAGMENT";
    private String mMovieType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            mPosterFragment = new PosterFragment();
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.poster_container, mPosterFragment)
                    .commit();
        }
        if (savedInstanceState != null) {
            mPosterFragment = (PosterFragment) getSupportFragmentManager().getFragment(savedInstanceState, FRAGMENT_NAME);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mPosterFragment != null) {
            if (mPosterFragment.isAdded()) {
                getSupportFragmentManager().putFragment(outState, FRAGMENT_NAME, mPosterFragment);
            }
        }
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
        }
        if (id == R.id.top_rated) {
            mMovieType = "top_rated";
        }
        return super.onOptionsItemSelected(item);
    }
}
