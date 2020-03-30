package com.disruption.popularmovies1.viewModel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.disruption.popularmovies1.BuildConfig;
import com.disruption.popularmovies1.model.Movie;
import com.disruption.popularmovies1.model.MovieResponse;
import com.disruption.popularmovies1.network.MovieApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieViewModel extends ViewModel {
    private static final String TAG = MovieViewModel.class.getSimpleName();
    private MutableLiveData<List<Movie>> mMovieList = new MutableLiveData<>();

    public MovieViewModel() {
        getMovieList();
    }

    public LiveData<List<Movie>> getMovieListObservable() {
        return mMovieList;
    }

    //This is the API Call
    private void getMovieList() {
        String api_key = BuildConfig.BingerSecretKey;
        Call<MovieResponse> call = MovieApiService.getMovieApiService().getMovies(api_key);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                if (response.body() != null) {
                    mMovieList.setValue(response.body().getResults());
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable throwable) {
                Log.e(TAG, throwable.toString());
            }
        });
    }

}
