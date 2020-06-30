package com.example.moviestage.presenter;

import com.example.moviestage.repository.MovieRepo;

import com.example.moviestage.model.Movie;
import com.example.moviestage.network.FetchMovieAsync;
import com.example.moviestage.view.MovieView;

import java.util.List;

public class MoviePresenter implements MovieRepo {

    final MovieView view;

    public MoviePresenter(MovieView view) {
        this.view = view;
    }

    public void fetchMovies(String sortBy){
        view.showProgress();
        new FetchMovieAsync(this).execute(sortBy);
    }


    @Override
    public void onSuccess(List<Movie> movies) {
        view.hideProgress();
        view.displayMovies(movies);
    }

    @Override
    public void onFailure(String message) {
        view.hideProgress();
        view.onDisplayError(message);
    }
}
