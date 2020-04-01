package com.disruption.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import com.disruption.popularmovies.adapter.MoviesAdapter;
import com.disruption.popularmovies.model.Movie;
import com.disruption.popularmovies.settings.SettingsActivity;
import com.disruption.popularmovies.utils.Constants;
import com.disruption.popularmovies.viewModel.FavouritesActivity;
import com.disruption.popularmovies.viewModel.main.MovieViewModel;
import com.disruption.popularmovies.viewModel.main.MovieViewModelFactory;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private MoviesAdapter mMoviesAdapter;
    private TextView mErrorTextView;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        mErrorTextView = findViewById(R.id.error_text);
        mProgressBar = findViewById(R.id.progressBar);

        mMoviesAdapter = new MoviesAdapter(this::onMovieClick);

        recyclerView.setAdapter(mMoviesAdapter);

        observeViewModelForMovies();
    }

    private void observeViewModelForMovies() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String sortBy = preferences.getString(
                getString(R.string.pref_sort_by_key),
                getString(R.string.pref_sort_by_pop_value)
        );

        MovieViewModelFactory factory = new MovieViewModelFactory(getApplication(), sortBy);

        MovieViewModel movieViewModel = new ViewModelProvider(this, factory).get(MovieViewModel.class);

        movieViewModel.getMovieResource().observe(this, movieResource -> {
            switch (movieResource.status) {
                case SUCCESS:
                    assert movieResource.data != null;
                    if (!movieResource.data.getResults().isEmpty()) {
                        mMoviesAdapter.submitList(movieResource.data.getResults());
                        mProgressBar.setVisibility(View.GONE);
                        mErrorTextView.setVisibility(View.GONE);
                    } else {
                        mProgressBar.setVisibility(View.GONE);
                        mErrorTextView.setVisibility(View.VISIBLE);
                        mErrorTextView.setText(getString(R.string.empty_result));
                    }
                    break;
                case ERROR:
                    mProgressBar.setVisibility(View.GONE);
                    mErrorTextView.setVisibility(View.VISIBLE);
                    if (movieResource.data != null) {
                        mErrorTextView.setText(getString(R.string.error_has_occurred, movieResource.message));
                    }
                    break;
                case LOADING:
                    mProgressBar.setVisibility(View.VISIBLE);
                    mErrorTextView.setVisibility(View.GONE);
                    break;
            }
        });
    }

    private void onMovieClick(Movie movie) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(Constants.MOVIE_EXTRA, movie);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_sort) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        } else if (item.getItemId() == R.id.action_favs) {
            startActivity(new Intent(this, FavouritesActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
