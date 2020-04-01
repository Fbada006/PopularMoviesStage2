package com.disruption.popularmovies.model.trailer;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieTrailerResponse {

    @SerializedName("results")
    private List<Trailer> mTrailers;

    private String errorMessage;

    public MovieTrailerResponse(List<Trailer> trailers, String errorMessage) {
        this.mTrailers = trailers;
        this.errorMessage = errorMessage;
    }

    public List<Trailer> getTrailers() {
        return mTrailers;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
