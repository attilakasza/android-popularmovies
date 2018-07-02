package com.attilakasza.popularmovies.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.attilakasza.popularmovies.R;
import com.attilakasza.popularmovies.fragments.OverviewFragment;
import com.attilakasza.popularmovies.fragments.ReviewFragment;
import com.attilakasza.popularmovies.fragments.TrailerFragment;

public class FragmentPagerAdapter extends android.support.v4.app.FragmentPagerAdapter {

    private Context mContext;
    private Bundle mBundle;

    public FragmentPagerAdapter(Context context, FragmentManager fm, Bundle bundle) {
        super(fm);
        mContext = context;
        mBundle = bundle;
    }

    // This determines the fragment for each tab
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            final OverviewFragment fragment = new OverviewFragment();
            fragment.setArguments(mBundle);
            return fragment;
        } else if (position == 1) {
            final TrailerFragment fragment = new TrailerFragment();
            fragment.setArguments(mBundle);
            return fragment;
        } else {
            final ReviewFragment fragment = new ReviewFragment();
            fragment.setArguments(mBundle);
            return fragment;
        }
    }

    // This determines the number of tabs
    @Override
    public int getCount() {
        return 3;
    }

    // This determines the title for each tab
    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        switch (position) {
            case 0:
                return mContext.getString(R.string.overview);
            case 1:
                return mContext.getString(R.string.trailer);
            case 2:
                return mContext.getString(R.string.review);
            default:
                return null;
        }
    }
}