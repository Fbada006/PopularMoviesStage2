package com.disruption.popularmovies.network;

import com.disruption.popularmovies.model.MovieResponse;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("movie/{sortBy}")
    Flowable<MovieResponse> getMovies(
            @Path("sortBy") String sortBy,
            @Query("api_key") String apiKey);
}