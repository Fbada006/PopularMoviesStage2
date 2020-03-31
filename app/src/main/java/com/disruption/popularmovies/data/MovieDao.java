package com.disruption.popularmovies.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.disruption.popularmovies.model.Movie;

import java.util.List;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM  movies")
    LiveData<List<Movie>> getAllMovies();

    @Query("SELECT * FROM movies WHERE movieId = :id")
    LiveData<Movie> loadMovieById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveAllMovies(List<Movie> movies);

    @Insert
    void addMovieToFavourites(Movie movie);

    @Delete
    void removeMovieFromFavourites(Movie movie);
}
