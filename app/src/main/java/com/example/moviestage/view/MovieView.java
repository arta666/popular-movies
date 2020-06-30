package com.example.moviestage.view;

import com.example.moviestage.model.Movie;

import java.util.List;

public interface MovieView {

    void displayMovies(List<Movie> movies);

    void onDisplayError(String error);

    void showProgress();
    void hideProgress();
}
