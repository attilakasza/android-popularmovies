package com.attilakasza.popularmovies.adapters;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.attilakasza.popularmovies.R;
import com.attilakasza.popularmovies.models.Trailer;
import com.squareup.picasso.Picasso;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {

    private Trailer[] mTrailer;
    private Context mContext;
    private String VIDEO_URL = "https://www.youtube.com/watch?v=";
    private String IMAGE_URL = "http://img.youtube.com/vi/";


    public TrailerAdapter(Context context) {
        mContext = context;
    }

    @Override
    public TrailerAdapter.TrailerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.trailer_list_item, viewGroup, false);
        return new TrailerAdapter.TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrailerAdapter.TrailerViewHolder holder, int position) {

        String name = mTrailer[position].getmName();
        final String key = mTrailer[position].getmKey();

        holder.tvName.setText(name);

        Picasso.with(mContext)
                .load(IMAGE_URL.concat(key).concat("/mqdefault.jpg"))
                .into(holder.ivTrailer);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + key));
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(VIDEO_URL + key));
                try {
                    mContext.startActivity(appIntent);
                } catch (ActivityNotFoundException ex) {
                    mContext.startActivity(webIntent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mTrailer == null) return 0;
        return mTrailer.length;
    }

    public Trailer[] swapTrailer(Trailer[] trailer) {
        if (mTrailer == trailer) {
            return null;
        }

        Trailer[] temp = mTrailer;
        this.mTrailer = trailer;

        if (trailer != null) {
            this.notifyDataSetChanged();
        }
        return temp;
    }

    class TrailerViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        ImageView ivTrailer;

        TrailerViewHolder(View view) {
            super(view);
            tvName = itemView.findViewById(R.id.tv_name);
            ivTrailer = itemView.findViewById(R.id.iv_trailer);
        }
    }
}