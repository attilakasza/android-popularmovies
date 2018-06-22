package com.attilakasza.popularmovies.utilities;

import com.attilakasza.popularmovies.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtils {

    private static final String RESULTS = "results";
    private static final String TITLE = "title";
    private static final String DATE = "release_date";
    private static final String POSTER = "poster_path";
    private static final String BACKDROP = "backdrop_path";
    private static final String VOTE = "vote_average";
    private static final String PLOT = "overview";


    public static Movie[] parseMovieJson(String json) {

        try {
            JSONObject root = new JSONObject(json);
            JSONArray arrayResult = root.getJSONArray(RESULTS);

            Movie[] result = new Movie[arrayResult.length()];

            for (int i = 0; i < arrayResult.length(); i++) {

                String title = arrayResult.getJSONObject(i).optString(TITLE);
                String date = arrayResult.getJSONObject(i).optString(DATE);
                String poster = arrayResult.getJSONObject(i).optString(POSTER);
                String backdrop = arrayResult.getJSONObject(i).optString(BACKDROP);
                String vote = arrayResult.getJSONObject(i).optString(VOTE);
                String plot = arrayResult.getJSONObject(i).optString(PLOT);

                result[i] = new Movie(title, date, poster, backdrop, vote, plot);
            }

            return result;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}