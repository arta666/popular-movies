package com.example.moviestage.db;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

public class MovieViewModelFactory extends ViewModelProvider.NewInstanceFactory {


    private final AppDb mDb;
    private final int movieId;

    public MovieViewModelFactory(AppDb mDb, int movieId) {
        this.mDb = mDb;
        this.movieId = movieId;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new MovieDetailViewModel(mDb,movieId);
    }
}
