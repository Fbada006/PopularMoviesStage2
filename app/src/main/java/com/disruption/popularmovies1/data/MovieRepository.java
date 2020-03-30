package com.disruption.popularmovies1.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;

import com.disruption.popularmovies1.BuildConfig;
import com.disruption.popularmovies1.model.Movie;
import com.disruption.popularmovies1.model.MovieResponse;
import com.disruption.popularmovies1.network.MovieApiService;
import com.disruption.popularmovies1.utils.MovieResource;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.schedulers.Schedulers;

public class MovieRepository {
    private MediatorLiveData<MovieResource<MovieResponse>> mMovieResource;
    private final String API_KEY = BuildConfig.BingerSecretKey;

    public LiveData<MovieResource<MovieResponse>> getMovies() {
        if (mMovieResource == null) {
            mMovieResource = new MediatorLiveData<>();
            mMovieResource.setValue(MovieResource.loading());

            final LiveData<MovieResource<MovieResponse>> source =
                    LiveDataReactiveStreams.fromPublisher(
                            MovieApiService.getMovieApiService().getMovies("popular", API_KEY)
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
