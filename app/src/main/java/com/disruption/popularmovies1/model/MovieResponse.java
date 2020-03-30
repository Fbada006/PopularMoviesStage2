package com.disruption.popularmovies1.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieResponse {

    @SerializedName("results")
    private List<Movie> results;

    private String errorMessage;

    public MovieResponse(List<Movie> results, String errorMessage) {
        this.results = results;
        this.errorMessage = errorMessage;
    }

    public List<Movie> getResults() {
        return results;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
