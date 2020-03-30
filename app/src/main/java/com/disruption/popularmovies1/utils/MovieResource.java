package com.disruption.popularmovies1.utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MovieResource<T> {

    @NonNull
    public final Status status;

    @Nullable
    public final T data;

    @Nullable
    public final String message;

    public MovieResource(@NonNull Status status, @Nullable T data, @Nullable String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> MovieResource<T> success(@Nullable T data) {
        return new MovieResource<>(Status.SUCCESS, data, null);
    }

    public static <T> MovieResource<T> error(@NonNull String msg, @Nullable T data) {
        return new MovieResource<>(Status.ERROR, data, msg);
    }

    public static <T> MovieResource<T> loading() {
        return new MovieResource<>(Status.LOADING, null, null);
    }

    public enum Status {SUCCESS, ERROR, LOADING}
}
