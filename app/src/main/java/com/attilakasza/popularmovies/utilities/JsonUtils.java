package com.attilakasza.popularmovies.utilities;

import com.attilakasza.popularmovies.models.Movie;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtils {

    private static final String RESULTS = "results";

    public static Movie[] parseMovieJson(String json) {

        try {
            JSONObject root = new JSONObject(json);
            Gson gson = new Gson();
            JSONArray arrayResult = root.getJSONArray(RESULTS);

            Movie[] result = new Movie[arrayResult.length()];

            for (int i = 0; i < arrayResult.length(); i++) {

                JSONObject movieJSON = arrayResult.getJSONObject(i);
                Movie movie = gson.fromJson(movieJSON.toString(), Movie.class);
                result[i] = movie;
            }

            return result;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}