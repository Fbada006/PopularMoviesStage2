package com.disruption.popularmovies1.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MovieResponse {
    @SerializedName("results")
    private List<Movie> results = new ArrayList<>();

    public List<Movie> getResults() {
        return results;
    }
}
