package com.example.moviestage.network;

import android.os.AsyncTask;

import com.example.moviestage.model.Review;
import com.example.moviestage.model.Video;
import com.example.moviestage.repository.MovieDetailRepo;
import com.example.moviestage.repository.ReviewRepo;
import com.example.moviestage.utils.MovieHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


public class FetchVideosAsync extends AsyncTask<String, Void, List<Video>> {

    private static final String TAG = FetchReviewsAsync.class.getSimpleName();

    private final MovieDetailRepo repo;

    public FetchVideosAsync(MovieDetailRepo repo) {
        this.repo = repo;
    }

    @Override
    protected List<Video> doInBackground(String... params) {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String reviewJsonStr;

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

            reviewJsonStr = builder.toString();

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

        return MovieHelper.getVideosDataFromJson(reviewJsonStr);
    }

    @Override
    protected void onPostExecute(List<Video> video) {
        super.onPostExecute(video);
        repo.onSuccess(video);
    }

}