package com.disruption.popularmovies.model;

import java.util.List;

public class MovieResponse {

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
