package com.example.moviestage.network;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.example.moviestage.repository.MovieRepo;
import com.example.moviestage.app.Constants;
import com.example.moviestage.app.Endpoint;
import com.example.moviestage.model.Movie;
import com.example.moviestage.utils.MovieHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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
            URL url = MovieHelper.makeUrl(params);
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

        return MovieHelper.getMoviesDataFromJson(moviesJsonStr);
    }

    @Override
    protected void onPostExecute(List<Movie> movies) {
        super.onPostExecute(movies);
            repo.onSuccess(movies);
    }

}


