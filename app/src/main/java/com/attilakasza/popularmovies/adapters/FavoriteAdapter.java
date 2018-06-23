package com.attilakasza.popularmovies.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.attilakasza.popularmovies.R;
import com.attilakasza.popularmovies.data.FavoriteContract;
import com.attilakasza.popularmovies.models.Movie;
import com.squareup.picasso.Picasso;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {

    private static final String POSTER_URL = "http://image.tmdb.org/t/p/w342";
    private final Context mContext;
    private final OnItemClickListener mListener;
    private Cursor mCursor;

    public FavoriteAdapter(Context context, OnItemClickListener listener) {
        mContext = context;
        mListener = listener;
    }

    @Override
    public FavoriteViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.poster_list_item, viewGroup, false);
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final FavoriteViewHolder holder, int position) {

        int idIndex = mCursor.getColumnIndex(FavoriteContract.FavoriteEntry.COLUMN_ID);
        int titleIndex = mCursor.getColumnIndex(FavoriteContract.FavoriteEntry.COLUMN_TITLE);
        int dateIndex = mCursor.getColumnIndex(FavoriteContract.FavoriteEntry.COLUMN_DATE);
        int posterIndex = mCursor.getColumnIndex(FavoriteContract.FavoriteEntry.COLUMN_POSTER);
        int backdropIndex = mCursor.getColumnIndex(FavoriteContract.FavoriteEntry.COLUMN_BACKDROP);
        int voteIndex = mCursor.getColumnIndex(FavoriteContract.FavoriteEntry.COLUMN_VOTE);
        int plotIndex = mCursor.getColumnIndex(FavoriteContract.FavoriteEntry.COLUMN_PLOT);
        int favoriteIndex = mCursor.getColumnIndex(FavoriteContract.FavoriteEntry.COLUMN_FAVORITE);

        mCursor.moveToPosition(position);

        String id = mCursor.getString(idIndex);
        String title = mCursor.getString(titleIndex);
        String date = mCursor.getString(dateIndex);
        String poster = mCursor.getString(posterIndex);
        String backdrop = mCursor.getString(backdropIndex);
        String vote = mCursor.getString(voteIndex);
        String plot = mCursor.getString(plotIndex);
        boolean favorite = mCursor.getInt(favoriteIndex) != 0;

        Picasso.with(mContext)
                .load(POSTER_URL.concat(poster))
                .fit()
                .into(holder.ivPosterItem);

        holder.movie = new Movie(id, title, date, poster, backdrop, vote, plot, favorite);

    }

    @Override
    public int getItemCount() {
        if (mCursor == null) return 0;
        return mCursor.getCount();
    }

    /**
     * When data changes and a re-query occurs, this function swaps the old Cursor
     * with a newly updated Cursor (Cursor cursor) that is passed in.
     */
    public Cursor swapCursor(Cursor cursor) {
        // check if this cursor is the same as the previous cursor (mCursor)
        if (mCursor == cursor) {
            return null;
        }

        Cursor temp = mCursor;
        this.mCursor = cursor;

        //check if this is a valid cursor, then update the cursor
        if (cursor != null) {
            this.notifyDataSetChanged();
        }
        return temp;
    }

    public interface OnItemClickListener {
        void onClick(Movie movie);
    }

    class FavoriteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView ivPosterItem;
        Movie movie;

        public FavoriteViewHolder(View itemView) {
            super(itemView);

            ivPosterItem = itemView.findViewById(R.id.iv_poster_item);
            ivPosterItem.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mListener.onClick(movie);
        }
    }
}