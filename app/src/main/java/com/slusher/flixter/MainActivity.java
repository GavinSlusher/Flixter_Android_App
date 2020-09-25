package com.slusher.flixter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.slusher.flixter.adapters.MovieAdapter;
import com.slusher.flixter.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {

    public static final String NOW_PLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

    List<Movie> movies;

//    Adding a tag to easily log data
    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView rvMovies = findViewById(R.id.rvMovies);
        movies = new ArrayList<>();

//        Create the adapter
//        note that MainActivity is an instance of the current context
        final MovieAdapter movieAdapter = new MovieAdapter(this, movies);

//        Set the adapter onto the recycler view
        rvMovies.setAdapter(movieAdapter);

//        set a layout manager on the recycler view so it knows how to layout the different views onto
//        the screen
        rvMovies.setLayoutManager(new LinearLayoutManager(this));

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(NOW_PLAYING_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
//                Debugger logging
                Log.d(TAG, "onSuccess");

//                Parse the json
                JSONObject jsonObject = json.jsonObject;
                try {
//                    Grab the results object from the JSON query
                    JSONArray results = jsonObject.getJSONArray("results");

//                    Log it
                    Log.i(TAG, "Results: " + results.toString());

//                    Get a list of movies using our Movie class method
                    movies.addAll(Movie.fromJsonArray(results));

//                    Whenever data changes for the adapter, let the adapter know so that it knows to
//                    rerender
                    movieAdapter.notifyDataSetChanged();

                    Log.i(TAG, "Movies" + movies.size());

                } catch (JSONException e) {
                    Log.e(TAG, "Hit json exception", e);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG, "onFailure");
            }
        });
    }
}