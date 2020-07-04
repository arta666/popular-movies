package com.example.moviestage.utils;

import android.net.Uri;
import android.util.Log;

import com.example.moviestage.app.Constants;
import com.example.moviestage.app.Endpoint;
import com.example.moviestage.model.Movie;
import com.example.moviestage.model.Review;
import com.example.moviestage.model.Video;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MovieHelper {

    public static List<Movie> getMoviesDataFromJson(String moviesJsonStr) {

        List<Movie> movieList = new ArrayList<>();
        try {
            JSONObject moviesJson = new JSONObject(moviesJsonStr);
            JSONArray resultArray = moviesJson.getJSONArray(Constants.RESULTS);

            for (int i = 0; i < resultArray.length(); i++) {
                JSONObject movieObject = resultArray.getJSONObject(i);
                Movie movie = new Movie();
                movie.setMovieId(movieObject.getInt(Constants.ID));
                movie.setOriginalTitle(movieObject.getString(Constants.ORIGINAL_TITLE));
                movie.setPosterPath(movieObject.getString(Constants.POSTER_PATH));
                movie.setBackdropPath(movieObject.getString(Constants.BACKDROP_PATH));
                movie.setOverview(movieObject.getString(Constants.OVERVIEW));
                movie.setVoteAverage(movieObject.getDouble(Constants.VOTE_AVERAGE));
                movie.setReleaseDate(movieObject.getString(Constants.RELEASE_DATE));
                movieList.add(movie);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
        return movieList;
    }

    public static List<Review> getReviewsDataFromJson(String reviewsJsonStr) {

        List<Review> reviewList = new ArrayList<>();
        try {
            JSONObject reviewsJson = new JSONObject(reviewsJsonStr);
            JSONArray resultArray = reviewsJson.getJSONArray(Constants.RESULTS);

            for (int i = 0; i < resultArray.length(); i++) {
                JSONObject reviewObject = resultArray.getJSONObject(i);
                Review review = new Review();
                review.setId(reviewObject.getString(Constants.ID));
                review.setAuthor(reviewObject.getString(Constants.AUTHOR));
                review.setContent(reviewObject.getString(Constants.CONTENT));

                reviewList.add(review);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
        return reviewList;
    }

    public static List<Video> getVideosDataFromJson(String reviewsJsonStr) {

        List<Video> videos = new ArrayList<>();
        try {
            JSONObject videosJson = new JSONObject(reviewsJsonStr);
            JSONArray resultArray = videosJson.getJSONArray(Constants.RESULTS);

            for (int i = 0; i < resultArray.length(); i++) {
                JSONObject videosObject = resultArray.getJSONObject(i);
                Video video = new Video();
                video.setId(videosObject.getString(Constants.ID));
                video.setName(videosObject.getString(Constants.NAME));
                video.setKey(videosObject.getString(Constants.KEY));
                video.setSite(videosObject.getString(Constants.SITE));
                video.setSize(videosObject.getInt(Constants.SIZE));
                video.setType(videosObject.getString(Constants.TYPE));

                videos.add(video);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
        return videos;
    }

    public static URL makeUrl(String... parameters) throws MalformedURLException {

        Uri.Builder uri = Uri.parse(Endpoint.BASE_URL)
                .buildUpon()
                .appendEncodedPath("movie")
                .appendQueryParameter("api_key", Constants.API_KEY);

        for (String param : parameters) {
            uri.appendPath(param);
        }
        uri.build();

        return new URL(uri.toString());

    }
}
