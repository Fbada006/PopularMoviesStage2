package com.disruption.popularmovies1.network;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.disruption.popularmovies1.utils.Constants.BASE_URL;

public class MovieApiService {
    private static Retrofit retrofit;

    private static HttpLoggingInterceptor mInterceptor =
            new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC);

    private static OkHttpClient mClient = new OkHttpClient.Builder()
            .addInterceptor(mInterceptor)
            .build();

    private static Retrofit retrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(mClient)
                    .build();
        }
        return retrofit;
    }

    public static ApiService getMovieApiService() {
        return retrofitInstance().create(ApiService.class);
    }
}
