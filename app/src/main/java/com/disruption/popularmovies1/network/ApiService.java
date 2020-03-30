package com.disruption.popularmovies1.network;

import com.disruption.popularmovies1.model.MovieResponse;

import io.reactivex.Flowable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("movie/top_rated")
    Flowable<MovieResponse> getMovies(@Query("api_key") String apiKey);
}