package com.disruption.popularmovies1.viewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

@SuppressWarnings("unchecked")
public class MovieViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private String sortBy;

    public MovieViewModelFactory(String sortBy) {
        this.sortBy = sortBy;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MovieViewModel.class)) {
            return (T) new MovieViewModel(sortBy);
        }

        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
