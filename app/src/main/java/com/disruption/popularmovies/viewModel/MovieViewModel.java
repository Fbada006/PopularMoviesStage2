package com.disruption.popularmovies.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.disruption.popularmovies.data.MovieRepository;
import com.disruption.popularmovies.model.MovieResponse;
import com.disruption.popularmovies.utils.MovieResource;

public class MovieViewModel extends ViewModel {
    public LiveData<MovieResource<MovieResponse>> mMovieResource;

    public MovieViewModel(String sortBy) {
        MovieRepository movieRepository = new MovieRepository();
        mMovieResource = movieRepository.getMovies(sortBy);
    }
}
