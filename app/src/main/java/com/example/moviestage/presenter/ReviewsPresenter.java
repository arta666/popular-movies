package com.example.moviestage.presenter;

import com.example.moviestage.app.Constants;
import com.example.moviestage.model.Movie;
import com.example.moviestage.model.Review;
import com.example.moviestage.network.FetchMovieAsync;
import com.example.moviestage.network.FetchReviewsAsync;
import com.example.moviestage.repository.MovieRepo;
import com.example.moviestage.repository.ReviewRepo;
import com.example.moviestage.view.MovieView;
import com.example.moviestage.view.ReviewsView;

import java.util.List;


public class ReviewsPresenter implements ReviewRepo {

    final ReviewsView view;

    public ReviewsPresenter(ReviewsView view) {
        this.view = view;
    }

    public void fetchReviews(int id){
        view.showProgress();
        new FetchReviewsAsync(this).execute(String.valueOf(id), Constants.FETCH_REVIEWS);
    }

    @Override
    public void onSuccess(List<Review> reviews) {
        view.hideProgress();
        view.displayReviews(reviews);
    }

    @Override
    public void onFailure(String message) {
        view.hideProgress();
        view.onDisplayError(message);
    }
}