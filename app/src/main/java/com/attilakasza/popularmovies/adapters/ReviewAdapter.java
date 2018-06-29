package com.attilakasza.popularmovies.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.attilakasza.popularmovies.R;
import com.attilakasza.popularmovies.models.Review;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>{

    private Review[] mReview;
    private final Context mContext;

    public ReviewAdapter(Context context){
        mContext = context;
    }

    @Override
    public ReviewAdapter.ReviewViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.review_list_item, viewGroup, false);
        return new ReviewAdapter.ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewAdapter.ReviewViewHolder holder, int position) {

        String author = mReview[position].getmAuthor();
        String content = mReview[position].getmContent();

        holder.tvAuthor.setText(author);
        holder.tvContent.setText(content);
    }

    @Override
    public int getItemCount() {
        if (mReview == null) return 0;
        return mReview.length;
    }

    public Review[] swapReview(Review[] review) {
        if (mReview == review) {
            return null;
        }

        Review[] temp = mReview;
        this.mReview = review;

        if (review != null) {
            this.notifyDataSetChanged();
        }
        return temp;
    }

    class ReviewViewHolder extends RecyclerView.ViewHolder{

        TextView tvAuthor;
        TextView tvContent;

        ReviewViewHolder(View view){
            super(view);
            tvAuthor = itemView.findViewById(R.id.tv_author);
            tvContent = itemView.findViewById(R.id.tv_content);
        }
    }
}