package com.attilakasza.popularmovies.utilities;

import android.net.Uri;

import com.attilakasza.popularmovies.BuildConfig;

import java.net.MalformedURLException;
import java.net.URL;

public class NetworkUtils {

    private final static String BASE_URL = "https://api.themoviedb.org/3/movie/";
    private final static String API = "api_key";
    private final static String API_KEY = BuildConfig.MOVIE_DB_API_KEY;

    public static URL buildUrl(String movieType) {

        Uri builtUri = Uri.parse(BASE_URL)
                .buildUpon()
                .appendPath(movieType)
                .appendQueryParameter(API, API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }
}
