package com.example.moviestage.db;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.moviestage.model.Movie;

public class MovieDetailViewModel extends ViewModel {

    private LiveData<Movie> movie;

    public MovieDetailViewModel(AppDb database, int movieId) {
        movie = database.movieDao().findById(movieId);
    }

    public LiveData<Movie> getMovie() {
        return movie;
    }
}