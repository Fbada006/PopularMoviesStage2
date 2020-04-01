package com.disruption.popularmovies.model.review;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieReviewResponse {

    @SerializedName("results")
    private List<Review> reviews;

    private String errorMessage;

    public MovieReviewResponse(List<Review> reviews, String errorMessage) {
        this.reviews = reviews;
        this.errorMessage = errorMessage;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
