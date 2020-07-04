package com.example.moviestage.view;

import com.example.moviestage.model.Movie;

import java.util.List;

public interface MovieView extends BaseView {

    void displayMovies(List<Movie> movies);

    void onDisplayError(String error);

}
