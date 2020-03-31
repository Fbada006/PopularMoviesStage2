package com.disruption.popularmovies.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;

import com.disruption.popularmovies.BuildConfig;
import com.disruption.popularmovies.model.Movie;
import com.disruption.popularmovies.model.MovieResponse;
import com.disruption.popularmovies.network.MovieApiService;
import com.disruption.popularmovies.utils.MovieResource;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.schedulers.Schedulers;

public class MovieRepository {
    private MediatorLiveData<MovieResource<MovieResponse>> mMovieResource;
    private final String API_KEY = BuildConfig.BingerSecretKey;

    public LiveData<MovieResource<MovieResponse>> getMovies(String sortBy) {
        if (mMovieResource == null) {
            mMovieResource = new MediatorLiveData<>();
            mMovieResource.setValue(MovieResource.loading());

            final LiveData<MovieResource<MovieResponse>> source =
                    LiveDataReactiveStreams.fromPublisher(
                            MovieApiService.getMovieApiService().getMovies(sortBy, API_KEY)
                                    .onErrorReturn(throwable -> {
                                        Movie movie = new Movie();
                                        movie.setMovieId(-1000000);
                                        List<Movie> movies = new ArrayList<>();
                                        movies.add(movie);
                                        return new MovieResponse(movies, throwable.getMessage());
                                    })
                                    .map(response -> {
                                        if (response.getResults().size() > 0) {
                                            if (response.getResults().get(0).getMovieId() == -1000000) {
                                                return MovieResource.error(response.getErrorMessage(), response);
                                            }
                                        }
                                        return MovieResource.success(response);
                                    })
                                    .subscribeOn(Schedulers.io())
                    );
            mMovieResource.addSource(source, listResource -> {
                mMovieResource.setValue(listResource);
                mMovieResource.removeSource(source);
            });
        }

        return mMovieResource;
    }
}
