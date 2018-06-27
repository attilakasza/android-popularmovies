package com.attilakasza.popularmovies;

import android.app.Application;

import com.attilakasza.popularmovies.utilities.ConnectivityReceiver;

public class PopularMovies extends Application {

    private static PopularMovies mInstance;

    public static synchronized PopularMovies getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }
}