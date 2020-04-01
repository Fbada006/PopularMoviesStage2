package com.disruption.popularmovies.network;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.disruption.popularmovies.utils.Constants.BASE_URL;

public class MovieApiService {
    private static Retrofit retrofit;

    private static HttpLoggingInterceptor mHttpLoggingInterceptor =
            new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC);

    private static OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
            .addInterceptor(mHttpLoggingInterceptor)
            .build();

    private static Retrofit retrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(mOkHttpClient)
                    .build();
        }
        return retrofit;
    }

    public static ApiService getMovieApiService() {
        return retrofitInstance().create(ApiService.class);
    }
}
