package com.example.moviestage.db;

import android.app.Application;

import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.moviestage.model.Movie;

import java.util.List;

public class MovieViewModel extends AndroidViewModel {

    public AppDb db;
    private LiveData<List<Movie>> movieList;

    public MovieViewModel(@NonNull Application application) {
        super(application);
        db = AppDb.getInstance(this.getApplication());
        movieList = db.movieDao().findAll();
    }


    public LiveData<List<Movie>> getMovieList() {
        return movieList;
    }

}