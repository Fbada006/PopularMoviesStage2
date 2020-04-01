package com.disruption.popularmovies.viewModel.favs;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

@SuppressWarnings("ALL")
public class FavsViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private Application mApplication;

    public FavsViewModelFactory(Application application) {
        mApplication = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(FavsViewModel.class)) {
            return (T) new FavsViewModel(mApplication);
        }

        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
