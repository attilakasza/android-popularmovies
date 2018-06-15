package com.attilakasza.popularmovies.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.attilakasza.popularmovies.R;
import com.attilakasza.popularmovies.models.Movie;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.PosterViewHolder>{

    private Movie[] mMovie;
    private final Context mContext;
    private final OnItemClickListener mListener;
    private static final String POSTER_URL = "http://image.tmdb.org/t/p/w185";


    public MovieAdapter(Movie[] movies, Context context, OnItemClickListener listener) {
        mMovie = movies;
        mContext = context;
        mListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    @Override
    public PosterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.poster_list_item, viewGroup, false);
        return new PosterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PosterViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mMovie.length;
    }

    class PosterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView ivPosterItem;

        PosterViewHolder(View itemView) {
            super(itemView);

            ivPosterItem = itemView.findViewById(R.id.iv_poster_item);
            ivPosterItem.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mListener.onItemClick(getAdapterPosition());
        }
    }
}