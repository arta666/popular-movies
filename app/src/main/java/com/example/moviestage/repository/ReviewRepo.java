package com.example.moviestage.repository;

import com.example.moviestage.model.Movie;
import com.example.moviestage.model.Review;

import java.util.List;

public interface ReviewRepo {

    void onSuccess(List<Review> reviews);

    void onFailure(String message);
}
