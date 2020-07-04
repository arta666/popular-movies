package com.example.moviestage.presenter;

import com.example.moviestage.app.Constants;
import com.example.moviestage.model.Review;
import com.example.moviestage.model.Video;
import com.example.moviestage.network.FetchReviewsAsync;
import com.example.moviestage.network.FetchVideosAsync;
import com.example.moviestage.repository.MovieDetailRepo;
import com.example.moviestage.repository.ReviewRepo;
import com.example.moviestage.view.MovieDetailView;
import com.example.moviestage.view.ReviewsView;

import java.util.List;


public class MovieDetailPresenter implements MovieDetailRepo {

    final MovieDetailView view;

    public MovieDetailPresenter(MovieDetailView view) {
        this.view = view;
    }

    public void getVideos(int id){
        view.showProgress();
        new FetchVideosAsync(this).execute(String.valueOf(id), Constants.FETCH_VIDEOS);

    }



    @Override
    public void onSuccess(List<Video> videos) {
        view.hideProgress();
        view.onDisplayResult(videos);
    }

    @Override
    public void onFailure(String message) {
        view.hideProgress();
        view.onDisplayError(message);
    }
}