package com.example.moviestage.network;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.example.moviestage.repository.MovieRepo;
import com.example.moviestage.app.Commons;
import com.example.moviestage.app.Endpoint;
import com.example.moviestage.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FetchMovieAsync extends AsyncTask<String, Void, List<Movie>> {

    private static final String TAG = FetchMovieAsync.class.getSimpleName();

    private final MovieRepo repo;

    public FetchMovieAsync(MovieRepo repo) {
        this.repo = repo;
    }

    @Override
    protected List<Movie> doInBackground(String... params) {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String moviesJsonStr;

        try {
            URL url = makeUrl(params);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder builder = new StringBuilder();

            if (inputStream == null){
                repo.onFailure("Input Stream error!");
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null){
                builder.append(line).append("\n");
            }

            if (builder.length() == 0){
                repo.onFailure("builder error!");
                return null;
            }

            moviesJsonStr = builder.toString();

        } catch (IOException e) {
            e.printStackTrace();
            repo.onFailure(e.getLocalizedMessage());
            return null;
        }finally {
            if (urlConnection != null){
                urlConnection.disconnect();
            }
            if (reader !=null){
                try {
                    reader.close();
                }catch (IOException e) {
                    repo.onFailure(e.getLocalizedMessage());
                    e.printStackTrace();
                }
            }
        }

        return getMoviesDataFromJson(moviesJsonStr);
    }

    @Override
    protected void onPostExecute(List<Movie> movies) {
        super.onPostExecute(movies);
            repo.onSuccess(movies);
    }

    private List<Movie> getMoviesDataFromJson(String moviesJsonStr) {

        List<Movie> movieList = new ArrayList<>();
        try {
            JSONObject moviesJson = new JSONObject(moviesJsonStr);
            JSONArray resultArray = moviesJson.getJSONArray(Commons.RESULTS);

            for (int i = 0; i < resultArray.length(); i++) {
                JSONObject movieObject = resultArray.getJSONObject(i);
                Movie movie = new Movie();
                movie.setOriginalTitle(movieObject.getString(Commons.ORIGINAL_TITLE));
                movie.setPosterPath(movieObject.getString(Commons.POSTER_PATH));
                movie.setBackdropPath(movieObject.getString(Commons.BACKDROP_PATH));
                movie.setOverview(movieObject.getString(Commons.OVERVIEW));
                movie.setVoteAverage(movieObject.getDouble(Commons.VOTE_AVERAGE));
                movie.setReleaseDate(movieObject.getString(Commons.RELEASE_DATE));
                movieList.add(movie);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
        return movieList;
    }

    private URL makeUrl(String... parameters) throws MalformedURLException {

        Uri.Builder uri = Uri.parse(Endpoint.BASE_URL)
                .buildUpon()
                .appendEncodedPath("movie")
                .appendQueryParameter("api_key", Commons.API_KEY);

        for (String param : parameters) {
            uri.appendPath(param);
        }
        uri.build();
        Log.d(TAG, "makeUrl: " + uri);

        return new URL(uri.toString());

    }
}


