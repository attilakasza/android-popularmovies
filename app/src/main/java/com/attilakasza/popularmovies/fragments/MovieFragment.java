package com.attilakasza.popularmovies.fragments;

import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.attilakasza.popularmovies.R;
import com.attilakasza.popularmovies.data.FavoriteContract;
import com.attilakasza.popularmovies.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieFragment extends Fragment {

    private Movie mMovie;
    private static final String MOVIE = "MOVIE";
    private static final String POSTER_URL = "http://image.tmdb.org/t/p/w185";

    @BindView(R.id.tv_title) TextView textTitle ;
    @BindView(R.id.tv_date) TextView textDate;
    @BindView(R.id.iv_poster) ImageView imagePoster;
    @BindView(R.id.tv_vote) TextView textVote ;
    @BindView(R.id.tv_plot) TextView textPlot ;

    public MovieFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie, container, false);
        ButterKnife.bind(this, rootView);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mMovie = bundle.getParcelable(MOVIE);
        }

        textTitle.setText(mMovie.getmTitle());
        textVote.setText(mMovie.getmVote());
        textPlot.setText(mMovie.getmPlotSynopsis());
        textDate.setText("(" + mMovie.getmDate().substring(0, 4) + ")");
        Picasso.with(getContext())
                .load(POSTER_URL.concat(mMovie.getmPoster()))
                .into(imagePoster);

        final FloatingActionButton fab = rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fab.getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.ic_favorite_border).getConstantState())) {
                    insertFavorite();
                    fab.setImageResource(R.drawable.ic_favorite);
                } else if (fab.getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.ic_favorite).getConstantState())) {
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

        return rootView;
    }

    private boolean isFavorite(String movieId) {
        Cursor savedFavorite;
        savedFavorite = getActivity().getContentResolver().query(
                FavoriteContract.FavoriteEntry.CONTENT_URI,
                null,
                FavoriteContract.FavoriteEntry.COLUMN_ID + "=" + movieId,
                null,
                null);

        if (savedFavorite.moveToNext()) {
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

        Uri uri = Objects.requireNonNull(getActivity()).getContentResolver().insert(FavoriteContract.FavoriteEntry.CONTENT_URI, movieValues);
        if(uri != null) {
            showMessage(R.string.saved);
        }
    }

    private void deleteFavorite() {
        Uri uri = FavoriteContract.FavoriteEntry.CONTENT_URI.buildUpon().appendPath(mMovie.getmId()).build();
        int favoriteDeleted = getActivity().getContentResolver().delete(uri, null, null);
        if (favoriteDeleted != 0) {
            showMessage(R.string.removed);
        }
    }

    private void showMessage(int action){
        Toast toast = Toast.makeText(getContext(), mMovie.getmTitle() + " " + getString(action) , Toast.LENGTH_LONG);
        View view = toast.getView();
        view.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);
        toast.show();
    }
}