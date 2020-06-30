package com.example.moviestage.activity;

import com.example.moviestage.model.Movie;

import java.util.List;

public interface MovieRepo {

    void onSuccess(List<Movie> movies);

    void onFailure(String message);
}
