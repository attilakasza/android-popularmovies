package com.attilakasza.popularmovies.activities;

import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.attilakasza.popularmovies.R;
import com.attilakasza.popularmovies.adapters.FragmentPagerAdapter;
import com.attilakasza.popularmovies.data.FavoriteContract;
import com.attilakasza.popularmovies.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieActivity extends AppCompatActivity {

    private Movie mMovie;
    private static final String MOVIE = "MOVIE";
    private static final String BACKDROP_URL = "http://image.tmdb.org/t/p/w780";

    @BindView(R.id.iv_backdrop) ImageView imageBackdrop;
    @BindView(R.id.detail_toolbar) Toolbar toolbar;
    @BindView(R.id.toolbar_layout) CollapsingToolbarLayout appBarLayout;
    @BindView(R.id.viewpager) ViewPager viewPager;
    @BindView(R.id.fab) FloatingActionButton fab;
    @BindView(R.id.sliding_tabs) TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mMovie = getIntent().getParcelableExtra(MOVIE);

        Bundle bundle = new Bundle();
        bundle.putParcelable(MOVIE, mMovie);

        FragmentPagerAdapter adapter = new FragmentPagerAdapter(this, getSupportFragmentManager(), bundle);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        if (appBarLayout != null) {
            appBarLayout.setTitle(mMovie.getmTitle());
        }

        Picasso.with(this)
                .load(BACKDROP_URL.concat(mMovie.getmBackdrop()))
                .into(imageBackdrop);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Objects.equals(fab.getDrawable().getConstantState(), getResources().getDrawable(R.drawable.ic_favorite_border).getConstantState())) {
                    insertFavorite();
                    fab.setImageResource(R.drawable.ic_favorite);
                } else if (Objects.equals(fab.getDrawable().getConstantState(), getResources().getDrawable(R.drawable.ic_favorite).getConstantState())) {
                    deleteFavorite();
                    fab.setImageResource(R.drawable.ic_favorite_border);
                }
            }
        });

        if (isFavorite(mMovie.getmId())) {
            fab.setImageResource(R.drawable.ic_favorite);
        }
        if (!isFavorite(mMovie.getmId())) {
            fab.setImageResource(R.drawable.ic_favorite_border);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean isFavorite(String movieId) {
        Cursor savedFavorite;
        savedFavorite = getContentResolver().query(
                FavoriteContract.FavoriteEntry.CONTENT_URI,
                null,
                FavoriteContract.FavoriteEntry.COLUMN_ID + "=" + movieId,
                null,
                null);

        if (Objects.requireNonNull(savedFavorite).moveToNext()) {
            savedFavorite.close();
            return true;
        } else {
            savedFavorite.close();
            return false;
        }
    }

    private void insertFavorite() {
        ContentValues movieValues = new ContentValues();
        movieValues.put(FavoriteContract.FavoriteEntry.COLUMN_ID, mMovie.getmId());
        movieValues.put(FavoriteContract.FavoriteEntry.COLUMN_TITLE, mMovie.getmTitle());
        movieValues.put(FavoriteContract.FavoriteEntry.COLUMN_DATE, mMovie.getmDate());
        movieValues.put(FavoriteContract.FavoriteEntry.COLUMN_POSTER, mMovie.getmPoster());
        movieValues.put(FavoriteContract.FavoriteEntry.COLUMN_BACKDROP, mMovie.getmBackdrop());
        movieValues.put(FavoriteContract.FavoriteEntry.COLUMN_VOTE, mMovie.getmVote());
        movieValues.put(FavoriteContract.FavoriteEntry.COLUMN_PLOT, mMovie.getmPlotSynopsis());
        movieValues.put(FavoriteContract.FavoriteEntry.COLUMN_FAVORITE, true);

        Uri uri = getContentResolver().insert(FavoriteContract.FavoriteEntry.CONTENT_URI, movieValues);
        if(uri != null) {
            showMessage(R.string.saved);
        }
    }

    private void deleteFavorite() {
        Uri uri = FavoriteContract.FavoriteEntry.CONTENT_URI.buildUpon().appendPath(mMovie.getmId()).build();
        int favoriteDeleted = getContentResolver().delete(uri, null, null);
        if (favoriteDeleted != 0) {
            showMessage(R.string.removed);
        }
    }

    private void showMessage(int action){
        Toast toast = Toast.makeText(this, mMovie.getmTitle() + " " + getString(action) , Toast.LENGTH_LONG);
        View view = toast.getView();
        view.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);
        toast.show();
    }
}