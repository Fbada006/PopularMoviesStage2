package com.disruption.popularmovies.viewModel.favs;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.disruption.popularmovies.data.MovieDatabase;
import com.disruption.popularmovies.model.Movie;
import com.disruption.popularmovies.repo.MovieRepository;

import java.util.List;

public class FavsViewModel extends AndroidViewModel {
    private LiveData<List<Movie>> favsList;

    FavsViewModel(Application application) {
        super(application);
        MovieRepository repository = new MovieRepository(MovieDatabase.getInstance(application).movieDao());
        favsList = repository.getAllFavs();
    }

    public LiveData<List<Movie>> getFavsList() {
        return favsList;
    }
}
