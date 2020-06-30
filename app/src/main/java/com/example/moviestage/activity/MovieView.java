package com.example.moviestage.activity;

import com.example.moviestage.model.Movie;

import java.util.List;

interface MovieView {

    void displayMovies(List<Movie> movies);

    void onDisplayError(String error);

    void showProgress();
    void hideProgress();
}
