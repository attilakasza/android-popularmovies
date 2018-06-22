package com.attilakasza.popularmovies.activities;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.attilakasza.popularmovies.R;
import com.attilakasza.popularmovies.models.Movie;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieActivity extends AppCompatActivity {

    private Movie mMovie;
    private static final String MOVIE = "MOVIE";
    private static final String POSTER_URL = "http://image.tmdb.org/t/p/w185";
    private static final String BACKDROP_URL = "http://image.tmdb.org/t/p/w780";


    @BindView(R.id.tv_date) TextView textDate;
    @BindView(R.id.iv_poster) ImageView imagePoster;
    @BindView(R.id.iv_backdrop) ImageView imageBackdrop;
    @BindView(R.id.tv_vote) TextView textVote ;
    @BindView(R.id.tv_plot) TextView textPlot ;
    @BindView(R.id.detail_toolbar) Toolbar toolbar;
    @BindView(R.id.toolbar_layout) CollapsingToolbarLayout appBarLayout;

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

        if (appBarLayout != null) {
            appBarLayout.setTitle(mMovie.getmTitle());
        }
        textVote.setText(mMovie.getmVote());
        textPlot.setText(mMovie.getmPlotSynopsis());
        textDate.setText("(" + mMovie.getmDate().substring(0, 4) + ")");
        Picasso.with(this)
                .load(POSTER_URL.concat(mMovie.getmPoster()))
                .into(imagePoster);
        Picasso.with(this)
                .load(BACKDROP_URL.concat(mMovie.getmBackdrop()))
                .into(imageBackdrop);
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
}
