package com.example.moviestage.view;

import com.example.moviestage.model.Review;

import java.util.List;

public interface ReviewsView extends BaseView {

    void displayReviews(List<Review> reviews);

    void onDisplayError(String error);
}
