package com.attilakasza.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private PosterFragment mPosterFragment;
    private static final String FRAGMENT_NAME = "POSTERFRAGMENT";

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
}
