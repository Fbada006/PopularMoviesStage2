package com.disruption.popularmovies.viewModel.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.disruption.popularmovies.data.MovieDatabase;
import com.disruption.popularmovies.model.MovieResponse;
import com.disruption.popularmovies.repo.MovieRepository;
import com.disruption.popularmovies.utils.Resource;

public class MovieViewModel extends AndroidViewModel {
    private LiveData<Resource<MovieResponse>> mMovieResource;

    public MovieViewModel(@NonNull Application application, String sortBy) {
        super(application);
        MovieRepository movieRepository = new MovieRepository(MovieDatabase.getInstance(application).movieDao());
        mMovieResource = movieRepository.getMovies(sortBy);
    }

    public LiveData<Resource<MovieResponse>> getMovieResource() {
        return mMovieResource;
    }
}
