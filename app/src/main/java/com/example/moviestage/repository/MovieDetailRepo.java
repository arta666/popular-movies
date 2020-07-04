package com.example.moviestage.repository;

import com.example.moviestage.model.Movie;
import com.example.moviestage.model.Video;

import java.util.List;

public interface MovieDetailRepo {

    void onSuccess(List<Video> videos);

    void onFailure(String message);
}
