package com.disruption.popularmovies.viewModel.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

@SuppressWarnings("unchecked")
public class MovieViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private String sortBy;
    private Application mApplication;

    public MovieViewModelFactory(Application application, String sortBy) {
        this.mApplication = application;
        this.sortBy = sortBy;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MovieViewModel.class)) {
            return (T) new MovieViewModel(mApplication, sortBy);
        }

        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
