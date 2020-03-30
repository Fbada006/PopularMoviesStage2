package com.disruption.popularmovies1.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.disruption.popularmovies1.data.MovieRepository;
import com.disruption.popularmovies1.model.MovieResponse;
import com.disruption.popularmovies1.utils.MovieResource;

public class MovieViewModel extends ViewModel {
    public LiveData<MovieResource<MovieResponse>> mMovieResource;

    public MovieViewModel() {
        MovieRepository movieRepository = new MovieRepository();
        mMovieResource = movieRepository.getMovies();
    }
}
