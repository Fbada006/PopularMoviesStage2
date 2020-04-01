package com.disruption.popularmovies.repo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;

import com.disruption.popularmovies.BuildConfig;
import com.disruption.popularmovies.data.MovieDao;
import com.disruption.popularmovies.model.Movie;
import com.disruption.popularmovies.model.MovieResponse;
import com.disruption.popularmovies.model.review.MovieReviewResponse;
import com.disruption.popularmovies.model.review.Review;
import com.disruption.popularmovies.model.trailer.MovieTrailerResponse;
import com.disruption.popularmovies.model.trailer.Trailer;
import com.disruption.popularmovies.network.MovieApiService;
import com.disruption.popularmovies.utils.Resource;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.schedulers.Schedulers;

public class MovieRepository {
    private MediatorLiveData<Resource<MovieResponse>> mMovieResource;
    private MediatorLiveData<Resource<MovieTrailerResponse>> mMovieTrailerResource;
    private MediatorLiveData<Resource<MovieReviewResponse>> mMovieReviewResource;

    private final String API_KEY = BuildConfig.BingerSecretKey;
    private MovieDao mMovieDao;

    public MovieRepository(MovieDao movieDao) {
        mMovieDao = movieDao;
    }

    public LiveData<Resource<MovieResponse>> getMovies(String sortBy) {
        if (mMovieResource == null) {
            mMovieResource = new MediatorLiveData<>();
            mMovieResource.setValue(Resource.loading());

            final LiveData<Resource<MovieResponse>> source =
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
                                                return Resource.error(response.getErrorMessage(), response);
                                            }
                                        }
                                        return Resource.success(response);
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

    public LiveData<Resource<MovieTrailerResponse>> getMoviesTrailers(int movieId) {
        if (mMovieTrailerResource == null) {
            mMovieTrailerResource = new MediatorLiveData<>();
            mMovieTrailerResource.setValue(Resource.loading());

            final LiveData<Resource<MovieTrailerResponse>> source =
                    LiveDataReactiveStreams.fromPublisher(
                            MovieApiService.getMovieApiService().getMovieTrailers(movieId, API_KEY)
                                    .onErrorReturn(throwable -> {
                                        Trailer trailer = new Trailer();
                                        trailer.setId("errorIdTrailer");
                                        List<Trailer> trailers = new ArrayList<>();
                                        trailers.add(trailer);
                                        return new MovieTrailerResponse(trailers, throwable.getMessage());
                                    })
                                    .map(response -> {
                                        if (response.getTrailers().size() > 0) {
                                            if (response.getTrailers().get(0).getId().equals("errorIdTrailer")) {
                                                return Resource.error(response.getErrorMessage(), response);
                                            }
                                        }
                                        return Resource.success(response);
                                    })
                                    .subscribeOn(Schedulers.io())
                    );

            mMovieTrailerResource.addSource(source, listResource -> {
                mMovieTrailerResource.setValue(listResource);
                mMovieTrailerResource.removeSource(source);
            });

        }
        return mMovieTrailerResource;
    }

    public LiveData<Resource<MovieReviewResponse>> getMovieReviews(int movieId) {
        if (mMovieReviewResource == null) {
            mMovieReviewResource = new MediatorLiveData<>();
            mMovieReviewResource.setValue(Resource.loading());

            final LiveData<Resource<MovieReviewResponse>> source =
                    LiveDataReactiveStreams.fromPublisher(
                            MovieApiService.getMovieApiService().getMovieReviews(movieId, API_KEY)
                                    .onErrorReturn(throwable -> {
                                        Review review = new Review();
                                        review.setId("errorIdReview");
                                        List<Review> reviews = new ArrayList<>();
                                        reviews.add(review);
                                        return new MovieReviewResponse(reviews, throwable.getMessage());
                                    })
                                    .map(response -> {
                                        if (response.getReviews().size() > 0) {
                                            if (response.getReviews().get(0).getId().equals("errorIdReview")) {
                                                return Resource.error(response.getErrorMessage(), response);
                                            }
                                        }
                                        return Resource.success(response);
                                    })
                                    .subscribeOn(Schedulers.io())
                    );

            mMovieReviewResource.addSource(source, listResource -> {
                mMovieReviewResource.setValue(listResource);
                mMovieReviewResource.removeSource(source);
            });

        }
        return mMovieReviewResource;
    }

    public LiveData<Movie> loadMovieById(int movieId) {
        return mMovieDao.loadMovieById(movieId);
    }
}
