package com.example.moviestage.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.moviestage.model.Movie;

import java.util.List;

public interface MovieDao {

    @Query("SELECT * FROM movies")
    LiveData<List<Movie>> findAll();

    @Insert
    void insert(Movie movie);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateOne(Movie movie);

    @Delete
    void deleteOne(Movie movie);

    @Query("SELECT * FROM movies WHERE id = :id")
    LiveData<Movie> findById(int id);
}