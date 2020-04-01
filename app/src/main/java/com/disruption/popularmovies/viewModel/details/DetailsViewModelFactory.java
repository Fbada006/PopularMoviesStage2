package com.disruption.popularmovies.viewModel.details;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

@SuppressWarnings("ALL")
public class DetailsViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private int movieId;
    private Application mApplication;

    public DetailsViewModelFactory(int movieId, Application application) {
        this.movieId = movieId;
        mApplication = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(DetailsViewModel.class)) {
            return (T) new DetailsViewModel(mApplication, movieId);
        }

        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
