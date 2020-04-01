package com.disruption.popularmovies.network;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.disruption.popularmovies.utils.Constants.BASE_URL;

public class MovieApiService {
    private static Retrofit retrofit;

    private static Retrofit retrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static ApiService getMovieApiService() {
        return retrofitInstance().create(ApiService.class);
    }

    public enum MovieFilter {
        FAVOURITES(null),
        TOP_RATED("top_rated"),
        POPULAR("popular");

        private String value;

        MovieFilter(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
