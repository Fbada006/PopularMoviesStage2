package com.disruption.popularmovies.network;

import com.disruption.popularmovies.model.MovieResponse;
import com.disruption.popularmovies.model.review.MovieReviewResponse;
import com.disruption.popularmovies.model.trailer.MovieTrailerResponse;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("movie/{sortBy}")
    Flowable<MovieResponse> getMovies(
            @Path("sortBy") String sortBy,
            @Query("api_key") String apiKey);

    @GET("movie/{movie_id}/reviews")
    Flowable<MovieReviewResponse> getMovieReviews(
            @Path("movie_id") int id,
            @Query("api_key") String apiKey
    );

    @GET("movie/{movie_id}/videos")
    Flowable<MovieTrailerResponse> getMovieTrailers(
            @Path("movie_id") int id,
            @Query("api_key") String apiKey
    );
}