package com.disruption.popularmovies.viewModel.details;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.disruption.popularmovies.data.MovieDatabase;
import com.disruption.popularmovies.model.review.MovieReviewResponse;
import com.disruption.popularmovies.model.trailer.MovieTrailerResponse;
import com.disruption.popularmovies.repo.MovieRepository;
import com.disruption.popularmovies.utils.Resource;

public class DetailsViewModel extends AndroidViewModel {
    private LiveData<Resource<MovieTrailerResponse>> mTrailerResourceLiveData;
    private LiveData<Resource<MovieReviewResponse>> mReviewResourceLiveData;

    public DetailsViewModel(@NonNull Application application, int movieId) {
        super(application);
        MovieRepository movieRepository = new MovieRepository(MovieDatabase.getInstance(application).movieDao());
        mTrailerResourceLiveData = movieRepository.getMoviesTrailers(movieId);
        mReviewResourceLiveData = movieRepository.getMovieReviews(movieId);
    }

    public LiveData<Resource<MovieTrailerResponse>> getTrailerResourceLiveData() {
        return mTrailerResourceLiveData;
    }

    public LiveData<Resource<MovieReviewResponse>> getReviewResourceLiveData() {
        return mReviewResourceLiveData;
    }
}
