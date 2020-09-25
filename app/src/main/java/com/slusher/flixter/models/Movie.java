package com.slusher.flixter.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.Policy;
import java.util.ArrayList;
import java.util.List;

public class Movie {

    String backdropPath;
    String posterPath;
    String title;
    String overview;

    public Movie(JSONObject jsonObject) throws JSONException {
        backdropPath = jsonObject.getString("backdrop_path");
       posterPath = jsonObject.getString("poster_path");
       title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");
    }

    public String getPosterPath() {
/*
        Completing the URL for the poster image
        Note that we are hardcoding the width of the image
        TO DO: call and append different sizes for the image
*/
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);
    }

    public String getBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", backdropPath);
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public static List<Movie> fromJsonArray(JSONArray movieJsonArray) throws JSONException {
//    Parameters: A JSON array of movies
//    Returns: A list of movie objects parsed from the JSON array
       List<Movie> movies = new ArrayList<>();

//       Add a movie at each position of the array
        for (int i = 0; i < movieJsonArray.length() ; i++) {
            movies.add(new Movie(movieJsonArray.getJSONObject(i)));
        }
        return movies;
    }
}
